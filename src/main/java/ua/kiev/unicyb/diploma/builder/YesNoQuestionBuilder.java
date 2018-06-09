package ua.kiev.unicyb.diploma.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.RadioButtonQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.YesNoQuestionDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

import java.util.ArrayList;
import java.util.List;

@Component
public class YesNoQuestionBuilder extends AbstractQuestionBuilder {

    @Autowired
    public YesNoQuestionBuilder(final FormattingElementsConverter formattingElementsConverter,
                                final QuestionAnswerConverter questionAnswerConverter,
                                final ParameterizedService parameterizedService) {
        super(formattingElementsConverter, questionAnswerConverter, parameterizedService);
    }


    @Override
    public QuestionEntity build() {
        final QuestionEntity questionEntity = buildCommon();

        questionEntity.setQuestionAnswers(buildQuestionAnswers((YesNoQuestionDescriptionEntity) questionDescription));

        return questionEntity;
    }

    private List<QuestionAnswerEntity> buildQuestionAnswers(final YesNoQuestionDescriptionEntity questionDescription) {
        final List<QuestionAnswerEntity> questionAnswerEntities = new ArrayList<>();

        final QuestionAnswerEntity correctAnswer = buildCorrectAnswer(questionDescription);
        final QuestionAnswerEntity incorrectAnswer = buildIncorrectAnswer(questionDescription);

        questionAnswerEntities.add(correctAnswer);
        questionAnswerEntities.add(incorrectAnswer);

        return questionAnswerEntities;
    }

    private QuestionAnswerEntity buildCorrectAnswer(YesNoQuestionDescriptionEntity questionDescription) {
        final QuestionAnswerEntity correctAnswer = new QuestionAnswerEntity();
        correctAnswer.setIsCorrect(true);
        correctAnswer.setAnswer(questionDescription.getAnswer() ? "true" : "false");
        return correctAnswer;
    }

    private QuestionAnswerEntity buildIncorrectAnswer(YesNoQuestionDescriptionEntity questionDescription) {
        final QuestionAnswerEntity incorrectAnswer = new QuestionAnswerEntity();
        incorrectAnswer.setIsCorrect(false);
        incorrectAnswer.setAnswer(questionDescription.getAnswer() ? "false" : "true");
        return incorrectAnswer;
    }
}
