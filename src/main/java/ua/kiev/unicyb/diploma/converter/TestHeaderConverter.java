package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.test.TestHeaderEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestHeaderConfigEntity;

@Component
public class TestHeaderConverter implements Converter<TestHeaderEntity, TestHeaderConfigEntity> {

    @Override
    public TestHeaderEntity toEntity(TestHeaderConfigEntity dto) {
        final TestHeaderEntity headerEntity = new TestHeaderEntity();
        headerEntity.setValue(dto.getValue());
        return headerEntity;
    }

    @Override
    public TestHeaderConfigEntity toDto(TestHeaderEntity entity) {
        return null;
    }
}
