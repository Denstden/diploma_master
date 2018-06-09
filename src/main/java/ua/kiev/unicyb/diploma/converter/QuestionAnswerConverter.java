package ua.kiev.unicyb.diploma.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class QuestionAnswerConverter {

    ParameterizedService parameterizedService;

    public QuestionAnswerEntity toEntity(AnswerDescriptionEntity dto, ParameterizedEntity parameterizedEntity) {
        final QuestionAnswerEntity entity = new QuestionAnswerEntity();

        String answer = dto.getAnswer();
        if (parameterizedEntity != null) {
            answer = parameterizedService.processParameterized(parameterizedEntity, answer);
        }
        entity.setAnswer(answer);
        entity.setIsCorrect(dto.getIsCorrect());

        return entity;
    }
}
