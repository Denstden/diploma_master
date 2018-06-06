package ua.kiev.unicyb.diploma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.repositories.TestConfigRepository;
import ua.kiev.unicyb.diploma.repositories.TestRepository;
import ua.kiev.unicyb.diploma.repositories.VariantConfigRepository;
import ua.kiev.unicyb.diploma.service.DatabaseService;
import ua.kiev.unicyb.diploma.service.TrainingTestService;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final TestConfigRepository testConfigRepository;
    private final VariantConfigRepository variantConfigRepository;
    private final TestRepository testRepository;
    private final TrainingTestService trainingTestService;


    @Autowired
    public DatabaseServiceImpl(final TestConfigRepository testConfigRepository,
                               final VariantConfigRepository variantConfigRepository,
                               final TestRepository testRepository,
                               final TrainingTestService trainingTestService) {
        this.testConfigRepository = testConfigRepository;
        this.variantConfigRepository = variantConfigRepository;
        this.testRepository = testRepository;
        this.trainingTestService = trainingTestService;
    }

    @Override
    public TestConfigEntity saveTestConfig(TestConfigEntity testConfig) {
        return testConfigRepository.save(testConfig);
    }

    @Override
    public VariantConfigEntity saveVariantConfig(VariantConfigEntity variantConfig) {
        return variantConfigRepository.save(variantConfig);
    }

    @Override
    public TestEntity saveTest(TestEntity testEntity) {
        final TestEntity test = testRepository.save(testEntity);

        if (TestType.TRAINING.equals(test.getTestType())) {
            final Integer countVariants = test.getVariants() == null ? 0 : test.getVariants().size();
            trainingTestService.addTest(test.getTestId(), countVariants);
        }

        return test;
    }
}
