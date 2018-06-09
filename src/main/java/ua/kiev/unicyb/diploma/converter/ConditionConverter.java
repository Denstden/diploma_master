package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.condition.ConditionEntity;
import ua.kiev.unicyb.diploma.domain.generated.Condition;

@Component
public class ConditionConverter implements Converter<ConditionEntity, Condition> {

    @Override
    public ConditionEntity toEntity(Condition dto) {
        final ConditionEntity entity = new ConditionEntity();

        entity.setValue(dto.getValue());

        return entity;
    }

    @Override
    public Condition toDto(ConditionEntity entity) {
        return null;
    }
}
