package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.configuration.variant.VariantConfigEntity;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.variant.VariantEntity;

import java.util.List;

public interface VariantService {
    VariantEntity getVariant(Long testId);
    List<VariantEntity> generateVariants(Integer count, VariantConfigEntity variantConfigEntity, TestEntity testEntity);
}
