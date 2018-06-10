package ua.kiev.unicyb.diploma.service.impl;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.domain.entity.configuration.test.TestType;
import ua.kiev.unicyb.diploma.domain.entity.test.TestEntity;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.AssignTestDto;
import ua.kiev.unicyb.diploma.exception.TestGenerationSystemException;
import ua.kiev.unicyb.diploma.repositories.TestRepository;
import ua.kiev.unicyb.diploma.repositories.user.UserRepository;
import ua.kiev.unicyb.diploma.security.AuthenticationService;
import ua.kiev.unicyb.diploma.service.TestService;
import ua.kiev.unicyb.diploma.service.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data
public class TestServiceImpl implements TestService {

    TestRepository testRepository;
    UserRepository userRepository;
    AuthenticationService authenticationService;

     @Override
    public Iterable<TestEntity> getTests(TestType testType) {
         if (TestType.TRAINING.equals(testType)) {
             return testRepository.findByTestType(testType);
         } else {
             return getControlTests();
         }
    }

    private Iterable<TestEntity> getControlTests() {
        final Collection<? extends GrantedAuthority> authorities = authenticationService.authorities();

        final Optional<? extends GrantedAuthority> tutorAuthority = authorities.stream()
                .filter(a -> a.getAuthority().equals("TUTOR"))
                .findFirst();

        if (tutorAuthority.isPresent()) {
            return testRepository.findByTestType(TestType.CONTROL);
        } else { // STUDENT
            final String currentUsername = authenticationService.getCurrentUsername();
            final UserEntity user = userRepository.findByUsername(currentUsername);

            return testRepository.findByTestTypeAndUsersIn(TestType.CONTROL, newHashSet(user));
        }
    }

    @Override
    public TestEntity assignTestToUsers(AssignTestDto assignTestDto) {
        if (assignTestDto == null || assignTestDto.getTestId() == null) {
            throw new TestGenerationSystemException("Test id should be not null");
        }

        final Long testId = assignTestDto.getTestId();
        final TestEntity test = testRepository.findOne(testId);

        if (test != null) {
            final List<Long> userIds = assignTestDto.getUserIds();
            final Iterable<UserEntity> users = userRepository.findAll(userIds);

            test.getUsers().addAll(newHashSet(users));
            return testRepository.save(test);
        } else {
            throw new TestGenerationSystemException("Can not find test by test id " + testId);
        }

    }

    @Override
    public TestEntity unAssignTest(TestEntity test) {
        final String username = authenticationService.getCurrentUsername();
        test.getUsers().removeIf(user -> user.getUsername().equals(username));
        return testRepository.save(test);
    }
}
