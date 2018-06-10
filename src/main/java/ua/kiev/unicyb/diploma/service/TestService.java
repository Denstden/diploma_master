package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.dto.request.AssignTestDto;

import java.util.List;

public interface TestService {
    Iterable<TestEntity> getTests(TestType testType);
    TestEntity assignTestToUsers(AssignTestDto assignTestDto);
    TestEntity unAssignTest(TestEntity testEntity);
}
