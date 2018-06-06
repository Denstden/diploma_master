package ua.kiev.unicyb.diploma.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.AbstractQuestionBuilder;
import ua.kiev.unicyb.diploma.converter.VariantFooterConverter;
import ua.kiev.unicyb.diploma.converter.VariantHeaderConverter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.*;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.*;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.description.QuestionSourceConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.factory.question.CheckboxQuestionFactory;
import ua.kiev.unicyb.diploma.factory.question.EssayQuestionFactory;
import ua.kiev.unicyb.diploma.factory.question.RadioButtonQuestionFactory;
import ua.kiev.unicyb.diploma.factory.question.YesNoQuestionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Getter
@Setter
@Slf4j
public class VariantFactory {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final VariantHeaderConverter variantHeaderConverter;
    private final VariantFooterConverter variantFooterConverter;
    private final CheckboxQuestionFactory checkboxQuestionFactory;
    private final RadioButtonQuestionFactory radioButtonQuestionFactory;
    private final YesNoQuestionFactory yesNoQuestionFactory;
    private final EssayQuestionFactory essayQuestionFactory;

    private VariantConfigEntity variantConfigEntity;

    @Autowired
    public VariantFactory(final VariantHeaderConverter variantHeaderConverter,
                          final VariantFooterConverter variantFooterConverter,
                          final CheckboxQuestionFactory checkboxQuestionFactory,
                          final RadioButtonQuestionFactory radioButtonQuestionFactory,
                          final YesNoQuestionFactory yesNoQuestionFactory,
                          final EssayQuestionFactory essayQuestionFactory) {
        this.variantHeaderConverter = variantHeaderConverter;
        this.variantFooterConverter = variantFooterConverter;
        this.checkboxQuestionFactory = checkboxQuestionFactory;
        this.radioButtonQuestionFactory = radioButtonQuestionFactory;
        this.yesNoQuestionFactory = yesNoQuestionFactory;
        this.essayQuestionFactory = essayQuestionFactory;
    }

    public VariantEntity generateNextVariant(final String name) {
        final VariantEntity variantEntity = new VariantEntity();

        variantEntity.setMark(variantConfigEntity.getPoints());
        variantEntity.setName(name);
        variantEntity.setVariantHeaders(variantHeaderConverter.toEntities(variantConfigEntity.getVariantHeader()));
        variantEntity.setVariantFooters(variantFooterConverter.toEntities(variantConfigEntity.getVariantFooter()));
        variantEntity.setQuestions(getQuestions());

        return variantEntity;
    }

    private List<QuestionEntity> getQuestions() {
        List<QuestionEntity> questions = new ArrayList<>();

        final List<QuestionConfigEntity> questionConfigs = variantConfigEntity.getQuestionConfigs();
        questionConfigs.forEach(questionConfig -> {
            final QuestionEntity question = processQuestionConfiguration(questionConfig);
            if (question != null) {
                questions.add(question);
            }
        });
        return questions;
    }

    private QuestionEntity processQuestionConfiguration(QuestionConfigEntity questionConfig) {
        if (questionConfig.getQuestionSourceConfigEntities().size() > 1) {
            return processAsCombinedQuestion(questionConfig);
        } else {
            final QuestionDescriptionEntity questionDescriptionEntity = questionConfig.getQuestionSourceConfigEntities().get(0).getQuestionDescriptionEntity();
            return processOneQuestionDescription(questionDescriptionEntity, questionConfig.getStrategy(), questionConfig.getMark());
        }
    }

    private QuestionEntity processAsCombinedQuestion(QuestionConfigEntity questionConfig) {
        final List<QuestionSourceConfigEntity> questionSourceConfigEntities = questionConfig.getQuestionSourceConfigEntities();

        final List<QuestionEntity> questionEntities = new ArrayList<>();
        if (questionSourceConfigEntities != null) {
            questionSourceConfigEntities.forEach(questionSourceConfigEntity -> {
                for (int i = 0; i < questionSourceConfigEntity.getCountQuestions(); i++) {
                    final QuestionEntity question = processOneQuestionDescription(questionSourceConfigEntity.getQuestionDescriptionEntity(),
                            questionConfig.getStrategy(), questionConfig.getMark());
                    questionEntities.add(question);
                }
            });
        }

        if (!questionEntities.isEmpty()) {
            Collections.shuffle(questionEntities);
            return questionEntities.get(0);
        } else {
            return null;
        }
    }

    private QuestionEntity processOneQuestionDescription(QuestionDescriptionEntity questionDescriptionEntity, EstimationStrategy estimationStrategy, Double mark) {
        final List<AbstractQuestionDescriptionEntity> questionDescriptions = questionDescriptionEntity.getQuestionConfigs();
        final AbstractQuestionDescriptionEntity randomQuestionDescription =
                questionDescriptions.get(RANDOM.nextInt(questionDescriptions.size()));

        if (randomQuestionDescription instanceof CheckboxQuestionDescriptionEntity) {
            initBuilder(checkboxQuestionFactory.getQuestionBuilder(), randomQuestionDescription, questionDescriptionEntity.getGlobalPreamble(), estimationStrategy, mark, QuestionType.CHECKBOX);
            return checkboxQuestionFactory.getQuestion();
        } else if (randomQuestionDescription instanceof RadioButtonQuestionDescriptionEntity) {
            initBuilder(radioButtonQuestionFactory.getQuestionBuilder(), randomQuestionDescription, questionDescriptionEntity.getGlobalPreamble(), estimationStrategy, mark, QuestionType.RADIOBUTTON);
            return radioButtonQuestionFactory.getQuestion();
        } else if (randomQuestionDescription instanceof YesNoQuestionDescriptionEntity) {
            initBuilder(yesNoQuestionFactory.getQuestionBuilder(), randomQuestionDescription, questionDescriptionEntity.getGlobalPreamble(), estimationStrategy, mark, QuestionType.YES_NO);
            return yesNoQuestionFactory.getQuestion();
        } else if (randomQuestionDescription instanceof EssayQuestionDescriptionEntity) {
            initBuilder(essayQuestionFactory.getQuestionBuilder(), randomQuestionDescription, questionDescriptionEntity.getGlobalPreamble(), estimationStrategy, mark, QuestionType.ESSAY);
            return essayQuestionFactory.getQuestion();
        }

        return null;
    }

    private void initBuilder(AbstractQuestionBuilder questionBuilder, AbstractQuestionDescriptionEntity questionDescription,
                             String globalPreamble, EstimationStrategy strategy, Double mark, QuestionType questionType) {
        questionBuilder.setQuestionDescription(questionDescription);
        questionBuilder.setGlobalPreamble(globalPreamble);
        questionBuilder.setMark(mark);
        questionBuilder.setStrategy(strategy);
        questionBuilder.setQuestionType(questionType);
    }

    public void initFactory(final VariantConfigEntity variantConfigEntity) {
        setVariantConfigEntity(variantConfigEntity);
    }
}
