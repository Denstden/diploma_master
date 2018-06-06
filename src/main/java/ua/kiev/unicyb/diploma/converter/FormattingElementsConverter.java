package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.FormattingElementsEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingElementsConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.format.FormattingStrategy;

@Component
public class FormattingElementsConverter implements Converter<FormattingElementsEntity, FormattingElementsConfigEntity> {
    @Override
    public FormattingElementsEntity toEntity(FormattingElementsConfigEntity dto) {
        final FormattingElementsEntity entity = new FormattingElementsEntity();

        entity.setCount(dto.getCount());
        entity.setFormattingStrategy(dto.getFormattingStrategy());

        return entity;
    }

    @Override
    public FormattingElementsConfigEntity toDto(FormattingElementsEntity entity) {
        return null;
    }
}
