package ua.kiev.unicyb.diploma.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

@Component
public class EssayQuestionBuilder extends AbstractQuestionBuilder {

    @Autowired
    public EssayQuestionBuilder(FormattingElementsConverter formattingElementsConverter, QuestionAnswerConverter questionAnswerConverter) {
        super(formattingElementsConverter, questionAnswerConverter);
    }

    @Override
    public QuestionEntity build() {
        return buildCommon();
    }
}
