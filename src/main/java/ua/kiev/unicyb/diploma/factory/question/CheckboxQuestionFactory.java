package ua.kiev.unicyb.diploma.factory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.CheckboxQuestionBuilder;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;

@Component
public class CheckboxQuestionFactory extends AbstractQuestionFactory {

    @Autowired
    public CheckboxQuestionFactory(final FormattingElementsConverter formattingElementsConverter,
                                   final QuestionAnswerConverter questionAnswerConverter) {
        questionBuilder = new CheckboxQuestionBuilder(formattingElementsConverter, questionAnswerConverter);
    }
}
