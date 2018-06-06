package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestHeaderConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.generated.TestConfig;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestConfigConverter implements Converter<TestConfigEntity, TestConfig> {
    @Override
    public TestConfigEntity toEntity(TestConfig dto) {
        if (dto == null) {
            return null;
        }

        TestConfigEntity entity = new TestConfigEntity();

        BigInteger countVariants = dto.getCountVariants();
        if (countVariants != null) {
            entity.setCountVariants(countVariants.intValue());
        }

        TestConfig.TestHeader testHeader = dto.getTestHeader();
        if (testHeader != null) {
            final List<TestHeaderConfigEntity> testHeaders = new ArrayList<>();
            testHeader.getLine().forEach(line -> {
                final TestHeaderConfigEntity testHeaderConfigEntity = new TestHeaderConfigEntity();
                testHeaderConfigEntity.setValue(line);
                testHeaders.add(testHeaderConfigEntity);
            });
            entity.setTestHeaders(testHeaders);
        }

        entity.setTestName(dto.getTestName());
        entity.setTestType(TestType.valueOf(dto.getTestType().toUpperCase()));

        return entity;
    }

    @Override
    public TestConfig toDto(TestConfigEntity entity) {
        return null;
    }
}
