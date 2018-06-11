package ua.kiev.unicyb.diploma.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.CheckboxQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckboxQuestionBuilder extends AbstractQuestionBuilder {

    @Autowired
    public CheckboxQuestionBuilder(final FormattingElementsConverter formattingElementsConverter,
                                   final QuestionAnswerConverter questionAnswerConverter,
                                   final ParameterizedService parameterizedService) {
        super(formattingElementsConverter, questionAnswerConverter, parameterizedService);
    }

    @Override
    public QuestionEntity build() {
        final QuestionEntity questionEntity = buildCommon();

        questionEntity.setQuestionAnswers(buildQuestionAnswers((CheckboxQuestionDescriptionEntity) questionDescription, questionEntity));

        return questionEntity;
    }

    private List<QuestionAnswerEntity> buildQuestionAnswers(final CheckboxQuestionDescriptionEntity questionDescription, final QuestionEntity questionEntity) {
        final Integer countCorrectAnswers = questionDescription.getCountCorrectAnswers();
        final Integer countIncorrectAnswers = questionDescription.getCountIncorrectAnswers();

        final List<AnswerDescriptionEntity> answers = questionDescription.getAnswers();

        final List<AnswerDescriptionEntity> correctAnswers = answers.stream().filter(AnswerDescriptionEntity::getIsCorrect).collect(Collectors.toList());
        final List<AnswerDescriptionEntity> incorrectAnswers = answers.stream().filter(answer -> !answer.getIsCorrect()).collect(Collectors.toList());

        final List<QuestionAnswerEntity> correct = getRandomNAnswersFromList(correctAnswers, countCorrectAnswers, questionEntity);
        final List<QuestionAnswerEntity> incorrect = getRandomNAnswersFromList(incorrectAnswers, countIncorrectAnswers, questionEntity);
        correct.addAll(incorrect);

        return correct;
    }




}
