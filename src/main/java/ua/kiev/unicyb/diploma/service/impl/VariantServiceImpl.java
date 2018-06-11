package ua.kiev.unicyb.diploma.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.UserQuestionAnswers;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.dto.response.AnswerDto;
import ua.kiev.unicyb.diploma.dto.response.QuestionDto;
import ua.kiev.unicyb.diploma.dto.response.QuestionWithAnswersDto;
import ua.kiev.unicyb.diploma.factory.VariantFactory;
import ua.kiev.unicyb.diploma.repositories.QuestionRepository;
import ua.kiev.unicyb.diploma.repositories.user.answer.UserQuestionAnswersRepository;
import ua.kiev.unicyb.diploma.repositories.config.TestConfigRepository;
import ua.kiev.unicyb.diploma.repositories.TestRepository;
import ua.kiev.unicyb.diploma.repositories.VariantRepository;
import ua.kiev.unicyb.diploma.service.TrainingTestService;
import ua.kiev.unicyb.diploma.service.VariantService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VariantServiceImpl implements VariantService {
    VariantRepository variantRepository;
    VariantFactory variantFactory;

    TestRepository testRepository;
    TestConfigRepository testConfigRepository;
    TrainingTestService trainingTestService;
    QuestionRepository questionRepository;
    UserQuestionAnswersRepository userQuestionAnswersRepository;

    @Override
    public VariantEntity getVariant(final Long testId) {
        final TestEntity test = testRepository.findOne(testId);

        if (TestType.TRAINING.equals(test.getTestType())) {
            return getNextTrainingVariant(testId);
        } else {
            return generateAndSaveNextControlVariant(test);
        }
    }

    @Override
    public List<VariantEntity> generateVariants(Integer count, VariantConfigEntity variantConfigEntity, TestEntity testEntity) {
        variantFactory.initFactory(variantConfigEntity);

        final List<VariantEntity> variants = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            VariantEntity variant;
            if (TestType.TRAINING.equals(testEntity.getTestType())) {
                variant = variantFactory.generateNextVariant(i + 1 + "");
            } else {
                final int countVariants = testEntity.getVariants().size();
                variant = variantFactory.generateNextVariant(countVariants + 1 + "");
            }
            variant.setTest(testEntity);
            variants.add(variant);
        }
        return variants;
    }

    @Override
    public List<QuestionWithAnswersDto> findByCheckedAndQuestionTypeForCurrentUser(Boolean isChecked, String questionType) {
        final List<QuestionWithAnswersDto> result = new ArrayList<>();

        final Iterable<QuestionEntity> questions = questionRepository.findByIsCheckedAndQuestionType(isChecked, QuestionType.valueOf(questionType));

        for (QuestionEntity questionEntity : questions) {
            final QuestionWithAnswersDto dto = new QuestionWithAnswersDto();
            setQuestion(dto, questionEntity);
            setAnswers(questionEntity, dto);
            result.add(dto);
        }

        return result;
    }

    private void setAnswers(QuestionEntity questionEntity, QuestionWithAnswersDto dto) {
        final Iterable<UserQuestionAnswers> answers = userQuestionAnswersRepository.findByQuestion(questionEntity);
        final List<AnswerDto> answerDtos = new ArrayList<>();
        for (UserQuestionAnswers userQuestionAnswers : answers) {
            final List<QuestionAnswerEntity> questionAnswers = userQuestionAnswers.getQuestionAnswers();

            questionAnswers.forEach(questionAnswer -> {
                final AnswerDto answerDto = new AnswerDto();
                answerDto.setAnswer(questionAnswer.getAnswer());
                answerDto.setAnswerId(questionAnswer.getAnswerId());

                answerDtos.add(answerDto);
            });
        }
        dto.setAnswers(answerDtos);
    }

    private void setQuestion(QuestionWithAnswersDto dto, QuestionEntity questionEntity) {
        final QuestionDto questionDto = new QuestionDto();
        questionDto.setPreamble(questionEntity.getPreamble());
        questionDto.setQuestionId(questionEntity.getQuestionId());
        questionDto.setQuestionType(questionEntity.getQuestionType());
        questionDto.setMark(questionEntity.getEstimation().getMark());

        dto.setQuestion(questionDto);
    }

    private VariantEntity getNextTrainingVariant(final Long testId) {
        final List<VariantEntity> entities = variantRepository.findByTest_TestId(testId);

        if (entities == null || entities.isEmpty()) {
            return null;
        } else {
            final Integer index = trainingTestService.getVariantNumber(testId);
            return entities.get(index);
        }
    }

    private VariantEntity generateAndSaveNextControlVariant(final TestEntity test) {
        final TestConfigEntity testConfig = testConfigRepository.findOne(test.getTestConfig().getTestConfigId());

        final List<VariantEntity> variantListFromOneElement = generateVariants(1, testConfig.getVariantConfig(), test);

        if (variantListFromOneElement == null) {
            return null;
        } else {
            final VariantEntity variant = variantListFromOneElement.get(0);
            test.getVariants().add(variant);
            variantRepository.save(variant);
//            testRepository.save(test);
            return variant;
        }
    }
}
