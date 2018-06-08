package ua.kiev.unicyb.diploma.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.EstimationEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.dto.request.QuestionAnswersDto;
import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;
import ua.kiev.unicyb.diploma.dto.response.VariantCheckDto;
import ua.kiev.unicyb.diploma.repositories.QuestionRepository;
import ua.kiev.unicyb.diploma.repositories.VariantRepository;
import ua.kiev.unicyb.diploma.service.CheckService;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class CheckServiceImpl implements CheckService {

    static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    VariantRepository variantRepository;
    QuestionRepository questionRepository;

    @Override
    public VariantCheckDto checkVariant(VariantAnswersDto variantAnswersDto) {
        final VariantCheckDto variantCheckDto = new VariantCheckDto();

        if (variantAnswersDto != null) {
            final Long variantId = variantAnswersDto.getVariantId();
            final VariantEntity variant = variantRepository.findOne(variantId);

            setComplete(variantCheckDto, variant);

            AtomicReference<Double> points = new AtomicReference<>(0d);

            final List<QuestionAnswersDto> questionAnswersList = variantAnswersDto.getQuestionAnswers();
            if (questionAnswersList != null) {
                questionAnswersList.forEach(questionAnswers -> {
                    final Double pointsForQuestion = checkQuestionAnswers(questionAnswers);
                    points.updateAndGet(v -> v + pointsForQuestion);
                });
            }

            final Double mark = variant.getMark();
            variantCheckDto.setTotal(mark);
            variantCheckDto.setPoints(Double.valueOf(DECIMAL_FORMAT.format(points.get())));
        }

        return variantCheckDto;
    }

    private void setComplete(VariantCheckDto variantCheckDto, VariantEntity variant) {
        final boolean isEssayQuestionPresent = variant.getQuestions().stream()
                .anyMatch(questionEntity -> QuestionType.ESSAY.equals(questionEntity.getQuestionType()));
        variantCheckDto.setIsComplete(!isEssayQuestionPresent);
    }

    private Double checkQuestionAnswers(final QuestionAnswersDto questionAnswers) {
        if (questionAnswers == null) {
            return 0d;
        }

        final Long questionId = questionAnswers.getQuestionId();
        final QuestionEntity question = questionRepository.findOne(questionId);

        if (question == null) {
            return 0d;
        } else {
            final List<String> answersFromClient = questionAnswers.getAnswerIds();
            final List<QuestionAnswerEntity> answersFromDb = question.getQuestionAnswers();
            final EstimationEntity estimation = question.getEstimation();
            return compareAnswers(questionId, answersFromClient, answersFromDb, estimation);
        }
    }

    private Double compareAnswers(final Long questionId, final List<String> answersFromClient, final List<QuestionAnswerEntity> answersFromDb,
                                  final EstimationEntity estimation) {
        if (answersFromDb == null) {
            //essay question
            log.info("{}: answersFromDb == null, should be essay question", questionId);
            return 0d;
        }

        if (estimation == null) {
            log.error("{}: estimation == null", questionId);
            return 0d;
        }

        final List<Long> answerIdsFromClient = new ArrayList<>();
        if (answersFromClient != null) {
            answersFromClient.forEach(strAnswer -> {
                try {
                    answerIdsFromClient.add(Long.parseLong(strAnswer));
                } catch (NumberFormatException nfe) {
                    // skip
                }
            });
        }

        switch (estimation.getEstimationStrategy()) {
            case EVENLY:
                return checkEvenly(answerIdsFromClient, answersFromDb, estimation.getMark());
            case WITH_FINES:
                return checkWithFines(answerIdsFromClient, answersFromDb, estimation.getMark());
            case ALL_NOTHING:
                return checkAllNothing(answerIdsFromClient, answersFromDb, estimation.getMark());
        }

        return 0d;
    }

    private Double checkEvenly(List<Long> answerIdsFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final AtomicReference<Double> result = new AtomicReference<>(0d);
        final Double part = mark / answersFromDb.size();

        answersFromDb.stream().filter(QuestionAnswerEntity::getIsCorrect).forEach(correctAnswer -> {
            if (answerIdsFromClient.contains(correctAnswer.getAnswerId())) {
                result.updateAndGet(v -> v + part);
            }
        });

        return result.get();
    }

    private Double checkWithFines(List<Long> answerIdsFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final AtomicReference<Double> result = new AtomicReference<>(0d);
        final Double part = mark / answersFromDb.size();

        answerIdsFromClient.forEach(answerId -> {
            final Optional<QuestionAnswerEntity> fromDbWithTheSameId = answersFromDb.stream()
                    .filter(ans -> ans.getAnswerId().equals(answerId))
                    .findFirst();

            if (fromDbWithTheSameId.isPresent() && fromDbWithTheSameId.get().getIsCorrect()) {
                result.updateAndGet(v -> v + part);
            } else {
                result.updateAndGet(v -> v - part);
            }
        });

        return result.get();
    }

    private Double checkAllNothing(List<Long> answerIdsFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final Set<Long> correctAnswerIds = answersFromDb.stream()
                .filter(QuestionAnswerEntity::getIsCorrect)
                .map(QuestionAnswerEntity::getAnswerId)
                .collect(Collectors.toSet());

        if (correctAnswerIds.equals(new HashSet<>(answerIdsFromClient))) {
            return mark;
        } else {
            return 0d;
        }
    }

}