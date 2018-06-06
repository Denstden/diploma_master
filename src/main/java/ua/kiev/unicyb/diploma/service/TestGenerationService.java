package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.generated.GlobalConfig;

public interface TestGenerationService {
    TestEntity loadConfig(GlobalConfig config);

}
