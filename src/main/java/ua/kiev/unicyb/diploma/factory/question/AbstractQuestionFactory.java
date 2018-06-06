package ua.kiev.unicyb.diploma.factory.question;

import lombok.Setter;import lombok.Getter;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.builder.AbstractQuestionBuilder;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;

@Getter@Setter
@Component
public abstract class AbstractQuestionFactory {
    protected AbstractQuestionBuilder questionBuilder;

    public QuestionEntity getQuestion() {
        return questionBuilder.build();
    }
}
