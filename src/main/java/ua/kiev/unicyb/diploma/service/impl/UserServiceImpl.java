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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;

    @Value("${new.user.role}")
    private String defaultRole;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository,
                           final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(userEntity.getPassword());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity register(final UserDto userDto) {
        final UserEntity user = userConverter.toEntity(userDto);
        user.setUserId(null);
        setDefaultRole(user);
        user.setIsActive(false);
        return save(user);
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

    @Override
    public Iterable<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity update(UserEntity user) {
        final UserEntity userFromDb = userRepository.findOne(user.getUserId());

        if (userFromDb == null) {
            throw new RuntimeException("User with id " + user.getUserId() + " not found!");
        }

        user.setPassword(userFromDb.getPassword());
        checkLastAdmin(user, userFromDb);

        updateRole(user);

        return userRepository.save(user);
    }

    private void checkLastAdmin(UserEntity user, UserEntity userFromDb) {
        if (userFromDb.getRoles() != null) {
            final Iterator<Role> iterator = userFromDb.getRoles().iterator();

            while (iterator.hasNext()) {
                final Role next = iterator.next();
                if ("ADMIN".equals(next.getName())) {
                    if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ADMIN")) {
                        throw new RuntimeException("User with id " + userFromDb.getUserId() + " and name " + userFromDb.getUsername() + " is the last admin of the system. Please assign admin role to another user.");
                    }
                }
            }
        }
    }

    private void updateRole(UserEntity user) {
        final Set<Role> roles = user.getRoles();

        if (!roles.isEmpty()) {
            final Iterator<Role> iterator = roles.iterator();
            if (iterator.hasNext()) {
                final Role next = iterator.next();
                final Role fromDb = roleRepository.findByName(next.getName());
                if (fromDb != null) {
                    user.setRoles(new HashSet<Role>(){{add(fromDb);}});
                }
            }
        }
    }

    private void setDefaultRole(UserEntity entity) {
        final String defaultRole = this.defaultRole == null ? "STUDENT" : this.defaultRole.toUpperCase();
        final Role role = roleRepository.findByName(defaultRole);

        if (role != null) {
            entity.setRoles(new HashSet<Role>(){{add(role);}});
        }
    }
}