package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;

public interface TestService {
    Iterable<TestEntity> getTests(TestType testType);
}
