package ua.kiev.unicyb.diploma.service;

import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.UserDto;

public interface UserService {
    UserEntity save(UserEntity userEntity);
    UserEntity findByUsername(String username);
    UserEntity register(UserDto userDto);
}
