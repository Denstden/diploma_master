package ua.kiev.unicyb.diploma.service;

import org.springframework.http.ResponseEntity;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.UserDto;

import java.util.List;

public interface UserService {
    UserEntity save(UserEntity userEntity);
    UserEntity findByUsername(String username);
    UserEntity register(UserDto userDto);
    Iterable<UserEntity> allUsersByRole(String role);

    Iterable<UserEntity> allUsers();

    UserEntity update(UserEntity user);
}
