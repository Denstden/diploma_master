package ua.kiev.unicyb.diploma.converter;

import org.springframework.stereotype.Component;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.UserDto;

@Component
public class UserConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserEntity toEntity(UserDto dto) {
        final UserEntity entity = new UserEntity();

        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUserName());
        entity.setPassword(dto.getPassword());

        return entity;
    }

    @Override
    public UserDto toDto(UserEntity entity) {
        return null;
    }
}
