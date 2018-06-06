package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantFooterEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantFooterConfigEntity;

@Component
public class VariantFooterConverter implements Converter<VariantFooterEntity, VariantFooterConfigEntity> {

    @Override
    public VariantFooterEntity toEntity(VariantFooterConfigEntity dto) {
        final VariantFooterEntity headerEntity = new VariantFooterEntity();
        headerEntity.setValue(dto.getValue());
        return headerEntity;
    }

    @Override
    public VariantFooterConfigEntity toDto(VariantFooterEntity entity) {
        return null;
    }
}
