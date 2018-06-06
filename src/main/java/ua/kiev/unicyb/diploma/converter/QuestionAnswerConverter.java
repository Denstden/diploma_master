package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.answer.QuestionAnswerEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.AnswerDescriptionEntity;

@Component
public class QuestionAnswerConverter implements Converter<QuestionAnswerEntity, AnswerDescriptionEntity> {
    @Override
    public QuestionAnswerEntity toEntity(AnswerDescriptionEntity dto) {
        final QuestionAnswerEntity entity = new QuestionAnswerEntity();

        entity.setAnswer(dto.getAnswer());
        entity.setIsCorrect(dto.getIsCorrect());

        return entity;
    }

    @Override
    public AnswerDescriptionEntity toDto(QuestionAnswerEntity entity) {
        return null;
    }
}
