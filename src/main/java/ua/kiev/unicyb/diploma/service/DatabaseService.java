package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;

public interface DatabaseService {

    TestConfigEntity saveTestConfig(TestConfigEntity testConfig);

    VariantConfigEntity saveVariantConfig(VariantConfigEntity variantConfig);

    TestEntity saveTest(TestEntity testEntity);
}
