package ua.kiev.unicyb.diploma.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.EstimationEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserQuestionAnswers;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserVariantAnswersEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.VariantCheckResultEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.dto.request.CheckQuestionDto;
import ua.kiev.unicyb.diploma.dto.request.VariantAnswersDto;
import ua.kiev.unicyb.diploma.repositories.QuestionRepository;
import ua.kiev.unicyb.diploma.repositories.user.answer.UserVariantAnswersRepository;
import ua.kiev.unicyb.diploma.repositories.check.VariantCheckResultRepository;
import ua.kiev.unicyb.diploma.repositories.VariantRepository;
import ua.kiev.unicyb.diploma.service.AnswersService;
import ua.kiev.unicyb.diploma.service.CheckService;
import ua.kiev.unicyb.diploma.service.TestService;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class CheckServiceImpl implements CheckService {

    static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    VariantRepository variantRepository;
    VariantCheckResultRepository variantCheckResultRepository;
    UserVariantAnswersRepository userVariantAnswersRepository;
    QuestionRepository questionRepository;
    AnswersService answersService;
    TestService testService;

    @Override
    public VariantCheckResultEntity checkVariant(final VariantAnswersDto variantAnswersDto) {
        final VariantCheckResultEntity variantCheckResultEntity = new VariantCheckResultEntity();

        if (variantAnswersDto != null) {
            final UserVariantAnswersEntity userVariantAnswers = answersService.saveAnswers(variantAnswersDto);
            final Long variantId = userVariantAnswers.getVariantId();
            final VariantEntity variant = variantRepository.findOne(variantId);

            if (TestType.CONTROL.equals(variant.getTest().getTestType())) {
                testService.unAssignTest(variant.getTest());
            }

            check(userVariantAnswers, variantCheckResultEntity);
            setComplete(variantCheckResultEntity, variant);
            setCheckedAllExceptEssay(variant);

            variantCheckResultEntity.setTotal(variant.getMark());

            userVariantAnswers.setVariantCheckResult(variantCheckResultRepository.save(variantCheckResultEntity));
            userVariantAnswersRepository.save(userVariantAnswers);
        }
        return variantCheckResultEntity;
    }

    @Override
    public VariantCheckResultEntity checkQuestion(CheckQuestionDto checkQuestionDto) {
        final Long questionId = checkQuestionDto.getQuestionId();
        final QuestionEntity question = questionRepository.findOne(questionId);

        final Optional<UserVariantAnswersEntity> optional = findByQuestion(question);
        if (optional.isPresent()) {
            final UserVariantAnswersEntity userVariantAnswers = optional.get();
            final VariantCheckResultEntity variantCheckResult = userVariantAnswers.getVariantCheckResult();

            if (!question.isChecked()) {
                check(checkQuestionDto, question, variantCheckResult);
                return variantCheckResultRepository.save(variantCheckResult);
            }
            return variantCheckResult;
        } else {
            throw new RuntimeException("Can not find user answers by questionId");
        }

    }

    private Optional<UserVariantAnswersEntity> findByQuestion(QuestionEntity questionEntity) {
        final Iterable<UserVariantAnswersEntity> all = userVariantAnswersRepository.findAll();
        for (UserVariantAnswersEntity entity : all) {
            for (UserQuestionAnswers userQuestionAnswers : entity.getUserQuestionAnswers()) {
                if (questionEntity.getQuestionId().equals(userQuestionAnswers.getQuestion().getQuestionId())) {
                    return Optional.of(entity);
                }
            }
        }
        return Optional.empty();
    }

    private void check(CheckQuestionDto checkQuestionDto, QuestionEntity question, VariantCheckResultEntity variantCheckResult) {
        question.setChecked(true);
        questionRepository.save(question);

        variantCheckResult.setPoints(variantCheckResult.getPoints() + checkQuestionDto.getMark());

        final VariantEntity variant = variantRepository.findByQuestionsIn(new HashSet<QuestionEntity>() {{
            add(question);
        }});

        setComplete(variantCheckResult, variant);
    }

    private void setCheckedAllExceptEssay(VariantEntity variantEntity) {
        variantEntity.getQuestions().forEach(question -> {
            if (!QuestionType.ESSAY.equals(question.getQuestionType())) {
                question.setChecked(true);
                questionRepository.save(question);
            }
        });
    }

    private void setComplete(VariantCheckResultEntity variantCheckResultEntity, VariantEntity variant) {
        final boolean isOneNotCheckedPresent = variant.getQuestions().stream()
                .anyMatch(questionEntity -> !questionEntity.isChecked());
        variantCheckResultEntity.setIsComplete(!isOneNotCheckedPresent);
    }

    private void check(UserVariantAnswersEntity userVariantAnswersEntity, VariantCheckResultEntity variantCheckResultEntity) {
        final AtomicReference<Double> points = new AtomicReference<>(0d);

        final List<UserQuestionAnswers> questionAnswersList = userVariantAnswersEntity.getUserQuestionAnswers();
        if (questionAnswersList != null) {
            questionAnswersList.forEach(questionAnswers -> {
                final Double pointsForQuestion = checkQuestionAnswers(questionAnswers);
                points.updateAndGet(v -> v + pointsForQuestion);
            });
        }

        variantCheckResultEntity.setPoints(Double.valueOf(DECIMAL_FORMAT.format(points.get())));
    }

    private Double checkQuestionAnswers(final UserQuestionAnswers questionAnswers) {
        if (questionAnswers == null) {
            return 0d;
        }

        final QuestionEntity question = questionAnswers.getQuestion();

        final List<QuestionAnswerEntity> answersFromUser = questionAnswers.getQuestionAnswers();
        final List<QuestionAnswerEntity> answersFromDb = question.getQuestionAnswers();

        final EstimationEntity estimation = question.getEstimation();

        return compareAnswers(question, answersFromUser, answersFromDb, estimation);
    }

    private Double compareAnswers(final QuestionEntity question, final List<QuestionAnswerEntity> answersFromClient, final List<QuestionAnswerEntity> answersFromDb,
                                  final EstimationEntity estimation) {
        if (answersFromDb == null) {
            //essay question
            log.info("{}: answersFromDb == null, should be essay question", question.getQuestionId());
            return 0d;
        }

        if (estimation == null) {
            log.error("{}: estimation == null", question.getQuestionId());
            return 0d;
        }

        switch (estimation.getEstimationStrategy()) {
            case EVENLY: {
                return checkEvenly(answersFromClient, answersFromDb, estimation.getMark());
            }
            case WITH_FINES: {
                return checkWithFines(answersFromClient, answersFromDb, estimation.getMark());
            }
            case ALL_NOTHING: {
                return checkAllNothing(answersFromClient, answersFromDb, estimation.getMark());
            }
        }

        return 0d;
    }

    private Double checkEvenly(List<QuestionAnswerEntity> answersFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final AtomicReference<Double> result = new AtomicReference<>(0d);
        final Double part = mark / answersFromDb.size();

        answersFromDb.stream().filter(QuestionAnswerEntity::getIsCorrect).forEach(correctAnswer -> {
            if (answersFromClient.contains(correctAnswer)) {
                result.updateAndGet(v -> v + part);
            }
        });

        return result.get();
    }

    private Double checkWithFines(List<QuestionAnswerEntity> answersFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final AtomicReference<Double> result = new AtomicReference<>(0d);
        final Double part = mark / answersFromDb.size();

        answersFromClient.forEach(answer -> {
            final Optional<QuestionAnswerEntity> fromDbWithTheSameId = answersFromDb.stream()
                    .filter(ans -> ans.equals(answer))
                    .findFirst();

            if (fromDbWithTheSameId.isPresent() && fromDbWithTheSameId.get().getIsCorrect()) {
                result.updateAndGet(v -> v + part);
            } else {
                result.updateAndGet(v -> v - part);
            }
        });

        return result.get();
    }

    private Double checkAllNothing(List<QuestionAnswerEntity> answersFromClient, List<QuestionAnswerEntity> answersFromDb, Double mark) {
        final Set<QuestionAnswerEntity> correctAnswers = answersFromDb.stream()
                .filter(QuestionAnswerEntity::getIsCorrect)
                .collect(Collectors.toSet());

        if (correctAnswers.equals(new HashSet<>(answersFromClient))) {
            return mark;
        } else {
            return 0d;
        }
    }

}