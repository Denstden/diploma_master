package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.AbstractParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.ParameterEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.DiapasonParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.ListParameterValue;
import ua.kiev.unicyb.diploma.domain.entity.configuration.question.parameterized.parameter.value.ParamEntity;
import ua.kiev.unicyb.diploma.domain.generated.Parameter;
import ua.kiev.unicyb.diploma.domain.generated.ParameterType;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParameterConverter implements Converter<ParameterEntity,Parameter> {
    @Override
    public ParameterEntity toEntity(final Parameter parameter) {
        final ParameterEntity entity = new ParameterEntity();

        entity.setParameterName(parameter.getName());
        entity.setParameterType(parameter.getType());
        entity.setParameterValue(convertParameterValue(parameter.getValue(), parameter.getType()));

        return entity;
    }

    private AbstractParameterValue convertParameterValue(final Parameter.Value value, final ParameterType parameterType) {

        if (value.getList() != null && !value.getList().isEmpty()) {
            return convertList(value.getList());
        }

        if (value.getDiapason() != null) {
            return convertDiapason(value.getDiapason());
        }

        return null;
    }

    private ListParameterValue convertList(List<String> list) {
        final ListParameterValue listParameterValue = new ListParameterValue();

        final List<ParamEntity> params = new ArrayList<>();
        list.forEach(elem -> {
            final ParamEntity param = new ParamEntity();
            param.setParamValue(elem);
            params.add(param);
        });
        listParameterValue.setParams(params);

        return listParameterValue;
    }

    private DiapasonParameterValue convertDiapason(Parameter.Value.Diapason diapason) {
        final DiapasonParameterValue diapasonParameterValue = new DiapasonParameterValue();

        diapasonParameterValue.setMinValue(diapason.getMin());
        diapasonParameterValue.setMaxValue(diapason.getMax());

        return diapasonParameterValue;
    }


    @Override
    public Parameter toDto(ParameterEntity entity) {
        return null;
    }
}
