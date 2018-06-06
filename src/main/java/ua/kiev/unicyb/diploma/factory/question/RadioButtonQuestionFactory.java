package ua.kiev.unicyb.diploma.factory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.RadioButtonQuestionBuilder;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;

@Component
public class RadioButtonQuestionFactory extends AbstractQuestionFactory {
    @Autowired
    public RadioButtonQuestionFactory(final FormattingElementsConverter formattingElementsConverter,
                                      final QuestionAnswerConverter questionAnswerConverter) {
        questionBuilder = new RadioButtonQuestionBuilder(formattingElementsConverter, questionAnswerConverter);
    }
}
