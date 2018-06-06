package ua.kiev.unicyb.diploma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.unicyb.diploma.converter.TestConfigConverter;
import ua.kiev.unicyb.diploma.converter.TestHeaderConverter;
import ua.kiev.unicyb.diploma.converter.VariantConfigConverter;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;
import ua.kiev.unicyb.diploma.factory.VariantFactory;
import ua.kiev.unicyb.diploma.service.DatabaseService;
import ua.kiev.unicyb.diploma.service.TestGenerationService;
import ua.kiev.unicyb.diploma.service.VariantService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestGenerationServiceImpl implements TestGenerationService {

    private final TestHeaderConverter testHeaderConverter;
    private final VariantService variantService;
    private final DatabaseService databaseService;
    private final TestConfigConverter testConfigConverter;
    private final VariantConfigConverter variantConfigConverter;

    @Autowired
    public TestGenerationServiceImpl(final TestHeaderConverter testHeaderConverter,
                                     final VariantService variantService,
                                     final DatabaseService databaseService,
                                     final TestConfigConverter testConfigConverter,
                                     final VariantConfigConverter variantConfigConverter) {
        this.testHeaderConverter = testHeaderConverter;
        this.variantService = variantService;
        this.databaseService = databaseService;
        this.testConfigConverter = testConfigConverter;
        this.variantConfigConverter = variantConfigConverter;
    }

    @Override
    public TestEntity loadConfig(GlobalConfig config) {
        TestConfigEntity testConfigEntity = testConfigConverter.toEntity(config.getTestConfig());
        VariantConfigEntity variantConfigEntity = variantConfigConverter.toEntity(config.getVariantConfig());
        testConfigEntity.setVariantConfig(variantConfigEntity);

        testConfigEntity = databaseService.saveTestConfig(testConfigEntity);
        variantConfigEntity = databaseService.saveVariantConfig(variantConfigEntity);

        TestEntity test;
        //for training
        if (testConfigEntity.getTestType() == TestType.TRAINING) {
            test = generateTrainingTest(variantConfigEntity, testConfigEntity);
        } else {
            test = createControlTest(testConfigEntity);
        }
        test.setTestConfig(testConfigEntity);
        databaseService.saveTest(test);

        return test;
    }

    private TestEntity generateTrainingTest(VariantConfigEntity variantConfigEntity, TestConfigEntity testConfigEntity) {
        if (variantConfigEntity == null || testConfigEntity == null) {
            return null;
        }

        final TestEntity test = createTest(testConfigEntity);

        final List<VariantEntity> variants = variantService.generateVariants(testConfigEntity.getCountVariants(), variantConfigEntity, test);
        test.setVariants(variants);

        return test;
    }

    private TestEntity createControlTest(TestConfigEntity testConfigEntity) {
        if (testConfigEntity == null) {
            return null;
        }

        return createTest(testConfigEntity);
    }

    private TestEntity createTest(TestConfigEntity testConfigEntity) {
        final TestEntity testEntity = new TestEntity();

        testEntity.setTestName(testConfigEntity.getTestName());
        testEntity.setTestType(testConfigEntity.getTestType());
        testEntity.setHeaders(testHeaderConverter.toEntities(testConfigEntity.getTestHeaders()));

        return testEntity;
    }
}
