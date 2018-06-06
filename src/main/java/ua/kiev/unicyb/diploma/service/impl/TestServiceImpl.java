package ua.kiev.unicyb.diploma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.repositories.TestRepository;
import ua.kiev.unicyb.diploma.service.TestService;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(final TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Iterable<TestEntity> getTests(TestType testType) {
        return testRepository.findByTestType(testType);
    }
}
