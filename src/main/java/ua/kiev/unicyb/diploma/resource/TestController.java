package ua.kiev.unicyb.diploma.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.service.TestService;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/control", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('TUTOR') or hasAuthority('STUDENT')")
    public Iterable<TestEntity> getControlTests() {
        return testService.getTests(TestType.CONTROL);
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('TUTOR') or hasAuthority('STUDENT')")
    public Iterable<TestEntity> getTrainingTests() {
        return testService.getTests(TestType.TRAINING);
    }
}
