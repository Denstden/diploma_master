package ua.kiev.unicyb.diploma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;
import ua.kiev.unicyb.diploma.factory.VariantFactory;
import ua.kiev.unicyb.diploma.repositories.config.TestConfigRepository;
import ua.kiev.unicyb.diploma.repositories.TestRepository;
import ua.kiev.unicyb.diploma.repositories.VariantRepository;
import ua.kiev.unicyb.diploma.service.TrainingTestService;
import ua.kiev.unicyb.diploma.service.VariantService;

import java.util.ArrayList;
import java.util.List;

@Service
public class VariantServiceImpl implements VariantService {
    private final VariantRepository variantRepository;
    private final VariantFactory variantFactory;

    private final TestRepository testRepository;
    private final TestConfigRepository testConfigRepository;
    private final TrainingTestService trainingTestService;


    @Autowired
    public VariantServiceImpl(final VariantRepository variantRepository, final VariantFactory variantFactory,
                              final TestRepository testRepository, final TestConfigRepository testConfigRepository,
                              final TrainingTestService trainingTestService) {
        this.variantRepository = variantRepository;
        this.variantFactory = variantFactory;
        this.testRepository = testRepository;
        this.testConfigRepository = testConfigRepository;
        this.trainingTestService = trainingTestService;
    }

    @Override
    public VariantEntity getVariant(final Long testId) {
        final TestEntity test = testRepository.findOne(testId);

        if (TestType.TRAINING.equals(test.getTestType())) {
            return getNextTrainingVariant(testId);
        } else {
            return generateAndSaveNextControlVariant(test);
        }
    }

    @Override
    public List<VariantEntity> generateVariants(Integer count, VariantConfigEntity variantConfigEntity, TestEntity testEntity) {
        variantFactory.initFactory(variantConfigEntity);

        final List<VariantEntity> variants = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            VariantEntity variant;
            if (TestType.TRAINING.equals(testEntity.getTestType())) {
                variant = variantFactory.generateNextVariant(i + 1 + "");
            } else {
                final int countVariants = testEntity.getVariants().size();
                variant = variantFactory.generateNextVariant(countVariants + 1 + "");
            }
            variant.setTest(testEntity);
            variants.add(variant);
        }
        return variants;
    }

    private VariantEntity getNextTrainingVariant(final Long testId) {
        final List<VariantEntity> entities = variantRepository.findByTest_TestId(testId);

        if (entities == null || entities.isEmpty()) {
            return null;
        } else {
            final Integer index = trainingTestService.getVariantNumber(testId);
            return entities.get(index);
        }
    }

    private VariantEntity generateAndSaveNextControlVariant(final TestEntity test) {
        final TestConfigEntity testConfig = testConfigRepository.findOne(test.getTestConfig().getTestConfigId());

        final List<VariantEntity> variantListFromOneElement = generateVariants(1, testConfig.getVariantConfig(), test);

        if (variantListFromOneElement == null) {
            return null;
        } else {
            final VariantEntity variant = variantListFromOneElement.get(0);
            test.getVariants().add(variant);
            variantRepository.save(variant);
//            testRepository.save(test);
            return variant;
        }
    }
}
