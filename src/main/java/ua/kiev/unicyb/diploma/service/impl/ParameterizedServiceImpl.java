package ua.kiev.unicyb.diploma.service.impl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.ParameterizedEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.condition.ConditionEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.ParameterEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.AbstractParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.DiapasonParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.ListParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.ParamEntity;
import ua.kiev.unicyb.diploma.domain.entity.question.ParameterizedValue;
import ua.kiev.unicyb.diploma.domain.entity.question.QuestionEntity;
import ua.kiev.unicyb.diploma.domain.generated.ParameterType;
import ua.kiev.unicyb.diploma.exception.ParameterizedException;
import ua.kiev.unicyb.diploma.service.ConditionService;
import ua.kiev.unicyb.diploma.service.ParameterSubstitutionService;
import ua.kiev.unicyb.diploma.service.ParameterizedService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ParameterizedServiceImpl implements ParameterizedService {
    private static final Long PARAMETERIZED_VALUE_GENERATION_RETRIES = 1000000000L;

    ConditionService conditionService;
    ParameterSubstitutionService parameterSubstitutionService;

    @Override
    public String processParameterized(final ParameterizedEntity entity, final String value, final QuestionEntity questionEntity) {
        if (entity == null) {
            return value;
        }

        return processParameterized(value, entity.getParameters(), entity.getConditions(), questionEntity);
    }

    private String processParameterized(final String value, final List<ParameterEntity> parameters,
                                        final List<ConditionEntity> conditions, final QuestionEntity questionEntity) {
        if (parameters == null || parameters.isEmpty()) {
            return value;
        }

        final Map<String, String> parameterValuesToSubstitute = getParameterValues(parameters, conditions);
        log.debug("Parameters to substitute: {}", parameterValuesToSubstitute.toString());
        final String result = parameterSubstitutionService.substitute(value, parameterValuesToSubstitute);

        final List<ParameterizedValue> parameterizedValues = new ArrayList<>();
        for (Map.Entry<String, String> parameter : parameterValuesToSubstitute.entrySet()) {
            final ParameterizedValue parameterizedValue = new ParameterizedValue();
            parameterizedValue.setName(parameter.getKey());
            parameterizedValue.setValue(parameter.getValue());
            parameterizedValues.add(parameterizedValue);
        }

        questionEntity.setParameterized(parameterizedValues);

        return result;
    }

    private Map<String, String> getParameterValues(final List<ParameterEntity> parameters,
                                                   final List<ConditionEntity> conditions) {

        Map<String, String> parameterValues = new HashMap<>();
        long retries = 0;
        boolean parameterValuesConformsConditions = false;

        while (!parameterValuesConformsConditions && retries < PARAMETERIZED_VALUE_GENERATION_RETRIES) {
            parameterValues = new HashMap<>();

            for (ParameterEntity parameterEntity : parameters) {
                final String parameterValue = getParameterValue(parameterEntity.getParameterValue(), parameterEntity.getParameterType());
                parameterValues.put(parameterEntity.getParameterName(), parameterValue);
            }

            parameterValuesConformsConditions = conditionService.conformsConditions(conditions, parameterValues);
            log.trace("Checking parameters: {}, conforms conditions: {}, retry: {}", parameterValues, parameterValuesConformsConditions, retries++);
        }

        if (parameterValuesConformsConditions) {
            return parameterValues;
        } else {
            throw new ParameterizedException("Can not generate parameter values to conform all conditions");
        }
    }

    private String getParameterValue(final AbstractParameterValue parameterValue, final ParameterType parameterType) {
        if (parameterValue instanceof DiapasonParameterValue) {
            return getOneParameterValueFromDiapason((DiapasonParameterValue) parameterValue, parameterType);
        } else
            return getOneListParameterValue((ListParameterValue) parameterValue, parameterType);
    }

    private String getOneParameterValueFromDiapason(final DiapasonParameterValue parameterValue,
                                                    final ParameterType parameterType) {
        final String minValue = parameterValue.getMinValue();
        final String maxValue = parameterValue.getMaxValue();

        Object currentValue = null;
        try {
            switch (parameterType) {
                case LONG: {
                    currentValue = ThreadLocalRandom.current().nextLong(Long.valueOf(minValue), Long.valueOf(maxValue));
                    break;
                }
                case BOOLEAN: {
                    currentValue = ThreadLocalRandom.current().nextBoolean();
                    break;
                }
                case INTEGER: {
                    currentValue = ThreadLocalRandom.current().nextInt(Integer.valueOf(minValue), Integer.valueOf(maxValue));
                    break;
                }
                default: {
                    currentValue = ThreadLocalRandom.current().nextDouble(Double.valueOf(minValue), Double.valueOf(maxValue));
                }
            }
        } catch (NumberFormatException e) {
            throw new ParameterizedException("Can not parse value: " + e.getMessage());
        }

        return currentValue.toString();
    }

    private String getOneListParameterValue(ListParameterValue parameterValue, ParameterType parameterType) {
        final List<ParamEntity> allParams = parameterValue.getParams();

        final int randomIndex = ThreadLocalRandom.current().nextInt(0, allParams.size());

        return allParams.get(randomIndex).getParamValue();
    }
}
