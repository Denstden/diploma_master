package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantHeaderEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantHeaderConfigEntity;

@Component
public class VariantHeaderConverter implements Converter<VariantHeaderEntity, VariantHeaderConfigEntity> {

    @Override
    public VariantHeaderEntity toEntity(VariantHeaderConfigEntity dto) {
        final VariantHeaderEntity headerEntity = new VariantHeaderEntity();
        headerEntity.setValue(dto.getValue());
        return headerEntity;
    }

    @Override
    public VariantHeaderConfigEntity toDto(VariantHeaderEntity entity) {
        return null;
    }
}
