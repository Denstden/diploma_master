package ua.kiev.unicyb.diploma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kiev.unicyb.diploma.converter.UserConverter;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.UserDto;
import ua.kiev.unicyb.diploma.repositories.user.RoleRepository;
import ua.kiev.unicyb.diploma.repositories.user.UserRepository;
import ua.kiev.unicyb.diploma.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${new.user.role}")
    private String defaultRole;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository,
                           final UserConverter userConverter, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity register(UserDto userDto) {
        final UserEntity entity = userConverter.toEntity(userDto);
        setDefaultRole(entity);
        return save(entity);
    }

    @Override
    public Iterable<UserEntity> allUsersByRole(String roleName) {

        final Role role = roleRepository.findByName(roleName);
        if (role == null) {
            return new ArrayList<>();
        }

        final Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        return userRepository.findByRolesIn(roleSet);
    }

    private void setDefaultRole(UserEntity entity) {
        final String defaultRole = this.defaultRole == null ? "STUDENT" : this.defaultRole.toUpperCase();
        final Role role = roleRepository.findByName(defaultRole);

        if (role != null) {
            entity.setRoles(new HashSet<Role>(){{add(role);}});
        }
    }
}