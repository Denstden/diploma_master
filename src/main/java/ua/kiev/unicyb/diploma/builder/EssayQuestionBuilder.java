package ua.kiev.unicyb.diploma.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.converter.FormattingElementsConverter;
import ua.kiev.unicyb.diploma.converter.QuestionAnswerConverter;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

@Component
public class EssayQuestionBuilder extends AbstractQuestionBuilder {

    @Autowired
    public EssayQuestionBuilder(final FormattingElementsConverter formattingElementsConverter,
                                final QuestionAnswerConverter questionAnswerConverter,
                                final ParameterizedService parameterizedService) {
        super(formattingElementsConverter, questionAnswerConverter, parameterizedService);
    }


    @Override
    public QuestionEntity build() {
        return buildCommon();
    }
}
