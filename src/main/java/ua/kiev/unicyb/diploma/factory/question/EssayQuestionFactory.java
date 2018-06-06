package ua.kiev.unicyb.diploma.factory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.EssayQuestionBuilder;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;

@Component
public class EssayQuestionFactory extends AbstractQuestionFactory {
    @Autowired
    public EssayQuestionFactory(final FormattingElementsConverter formattingElementsConverter,
                                final QuestionAnswerConverter questionAnswerConverter) {
        questionBuilder = new EssayQuestionBuilder(formattingElementsConverter, questionAnswerConverter);
    }
}
