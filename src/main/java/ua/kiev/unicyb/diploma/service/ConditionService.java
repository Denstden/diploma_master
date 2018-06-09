package ua.kiev.unicyb.diploma.service;

import com.creativewidgetworks.expressionparser.Parser;
import com.creativewidgetworks.expressionparser.Value;
import com.creativewidgetworks.expressionparser.ValueType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.condition.ConditionEntity;
import ua.kiev.unicyb.diploma.exception.ParameterizedException;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConditionService {

    ParameterSubstitutionService parameterSubstitutionService;

    public boolean conformsConditions(final List<ConditionEntity> conditionEntities, final Map<String, String> parameterValues) {
        if (conditionEntities == null || conditionEntities.isEmpty()) {
            log.trace("Empty conditions");
            return true;
        }

        for (ConditionEntity conditionEntity : conditionEntities) {
            final boolean conformsOneCondition = conformsCondition(conditionEntity, parameterValues);
            if (!conformsOneCondition) {
                log.trace("Condition not conforms: {}, parameters: {}", conditionEntity.getValue(), parameterValues);
                return false;
            }
        }

        log.trace("All conditions conforms");
        return true;
    }

    private boolean conformsCondition(final ConditionEntity conditionEntity, final Map<String, String> parameterValues) {
        final String condition = conditionEntity.getValue();
        final String expression = parameterSubstitutionService.substitute(condition, parameterValues);

        final Value value = new Parser().eval(expression);
        if (ValueType.BOOLEAN.equals(value.getType())) {
            return value.asBoolean();
        } else {
            throw new ParameterizedException("Condition with id " + conditionEntity.getConditionId() + " should be boolean");
        }
    }
}
