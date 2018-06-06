package ua.kiev.unicyb.diploma.factory.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.YesNoQuestionBuilder;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;

@Component
public class YesNoQuestionFactory extends AbstractQuestionFactory {
    @Autowired
    public YesNoQuestionFactory(final FormattingElementsConverter formattingElementsConverter,
                                final QuestionAnswerConverter questionAnswerConverter) {
        questionBuilder = new YesNoQuestionBuilder(formattingElementsConverter, questionAnswerConverter);
    }
}
