package ua.kiev.unicyb.diploma.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.dto.request.AssignTestDto;
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

    @GetMapping
    @PreAuthorize("hasAuthority('TUTOR') or hasAuthority('STUDENT')")
    public Iterable<TestEntity> getByTestType(@RequestParam(value = "type") String type) {
        final TestType testType = TestType.valueOf(type.toUpperCase());
        return testService.getTests(testType);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TUTOR')")
    public ResponseEntity<TestEntity> assignTestToUsers(@RequestBody AssignTestDto assignTestDto) {
        return new ResponseEntity<>(testService.assignTestToUsers(assignTestDto), HttpStatus.OK);
    }


}
