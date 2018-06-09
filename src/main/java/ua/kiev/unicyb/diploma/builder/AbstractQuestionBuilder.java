package ua.kiev.unicyb.diploma.builder;

import lombok.Setter;import lombok.Getter;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.EstimationEntity;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.EstimationStrategy;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.AbstractQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionType;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter@Setter
public abstract class AbstractQuestionBuilder {
    private static final String PREAMBLE_SEPARATOR = " ";

    protected String globalPreamble;
    protected AbstractQuestionDescriptionEntity questionDescription;
    protected EstimationStrategy strategy;
    protected Double mark;
    protected QuestionType questionType;

    protected final QuestionAnswerConverter answerConverter;
    private final FormattingElementsConverter formattingElementsConverter;
    private final ParameterizedService parameterizedService;

    public AbstractQuestionBuilder(final FormattingElementsConverter formattingElementsConverter,
                                   final QuestionAnswerConverter questionAnswerConverter,
                                   final ParameterizedService parameterizedService) {
        this.answerConverter = questionAnswerConverter;
        this.formattingElementsConverter = formattingElementsConverter;
        this.parameterizedService = parameterizedService;
    }

    public abstract QuestionEntity build();

    protected QuestionEntity buildCommon() {
        final QuestionEntity questionEntity = new QuestionEntity();

        questionEntity.setPreamble(concatPreambles());
        questionEntity.setQuestionType(questionType);
        questionEntity.setFormattingElements(formattingElementsConverter.toEntity(questionDescription.getFormattingElements()));
        questionEntity.setHashTag(questionDescription.getHashTag());
        questionEntity.setEstimation(convertEstimation(strategy, mark));

        return questionEntity;
    }

    protected boolean isParameterized() {
        return questionDescription.getParameterized() != null;
    }

    private String concatPreambles() {
        final StringBuilder result = new StringBuilder();
        if (globalPreamble != null) {
            result.append(globalPreamble);
        }

        String preamble = questionDescription.getPreamble();
        if (preamble != null) {
            preamble = processParameterized(questionDescription.getParameterized(), preamble);
            result.append(PREAMBLE_SEPARATOR).append(preamble);
        }

        return result.toString();
    }

    private String processParameterized(ParameterizedEntity parameterized, String preamble) {
        if (parameterized != null) {
            return parameterizedService.processParameterized(parameterized, preamble);
        } else {
            return preamble;
        }
    }

    private EstimationEntity convertEstimation(EstimationStrategy strategy, Double mark) {
        final EstimationEntity entity = new EstimationEntity();
        entity.setMark(mark);
        entity.setEstimationStrategy(strategy);
        return entity;
    }

    protected List<QuestionAnswerEntity> getRandomNAnswersFromList(final List<AnswerDescriptionEntity> answers,
                                                                 final Integer count) {
        final List<QuestionAnswerEntity> randomNAnswers = new ArrayList<>();

        Collections.shuffle(answers);

        for (int i = 0; i < count; i++) {
            randomNAnswers.add(answerConverter.toEntity(answers.get(i), questionDescription.getParameterized()));
        }

        return randomNAnswers;
    }
}
