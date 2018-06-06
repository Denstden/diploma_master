package ua.kiev.unicyb.diploma.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.CheckboxQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.RadioButtonQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RadioButtonQuestionBuilder extends AbstractQuestionBuilder {

    @Autowired
    public RadioButtonQuestionBuilder(FormattingElementsConverter formattingElementsConverter, QuestionAnswerConverter questionAnswerConverter) {
        super(formattingElementsConverter, questionAnswerConverter);
    }

    @Override
    public QuestionEntity build() {
        final QuestionEntity questionEntity = buildCommon();

        questionEntity.setQuestionAnswers(buildQuestionAnswers((RadioButtonQuestionDescriptionEntity) questionDescription));

        return questionEntity;
    }

    private List<QuestionAnswerEntity> buildQuestionAnswers(final RadioButtonQuestionDescriptionEntity questionDescription) {
        final Integer countCorrectAnswers = 1;
        final Integer countIncorrectAnswers = questionDescription.getCountAnswers() - 1;

        final List<AnswerDescriptionEntity> answers = questionDescription.getAnswers();

        final List<AnswerDescriptionEntity> correctAnswers = answers.stream().filter(AnswerDescriptionEntity::getIsCorrect).collect(Collectors.toList());
        final List<AnswerDescriptionEntity> incorrectAnswers = answers.stream().filter(answer -> !answer.getIsCorrect()).collect(Collectors.toList());

        final QuestionAnswerEntity correct = getRandomNAnswersFromList(correctAnswers, countCorrectAnswers).get(0);
        final List<QuestionAnswerEntity> incorrect = getRandomNAnswersFromList(incorrectAnswers, countIncorrectAnswers);
        incorrect.add(correct);

        return incorrect;
    }
}
