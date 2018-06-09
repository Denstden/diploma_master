package ua.kiev.unicyb.diploma.converter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;
import ua.kiev.unicyb.diploma.domain.generated.Parameterized;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data
public class ParameterizedConverter implements Converter<ParameterizedEntity, Parameterized> {

    ParameterConverter parameterConverter;
    ConditionConverter conditionConverter;

    @Override
    public ParameterizedEntity toEntity(Parameterized dto) {
        if (dto == null) {
            return null;
        }
        final ParameterizedEntity entity = new ParameterizedEntity();

        convertConditions(dto.getConditions(), entity);
        convertParameters(dto.getParameters(), entity);

        return entity;
    }

    private void convertConditions(Parameterized.Conditions conditions, ParameterizedEntity entity) {
        if (conditions != null) {
            entity.setConditions(conditionConverter.toEntities(conditions.getCondition()));
        }
    }

    private void convertParameters(Parameterized.Parameters parameters, ParameterizedEntity entity) {
        if (parameters != null) {
            entity.setParameters(parameterConverter.toEntities(parameters.getParameter()));
        }
    }

    @Override
    public Parameterized toDto(ParameterizedEntity entity) {
        return null;
    }
}
