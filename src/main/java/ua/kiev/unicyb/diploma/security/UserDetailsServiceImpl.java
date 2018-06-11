package ua.kiev.unicyb.diploma.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.repositories.user.RoleRepository;
import ua.kiev.unicyb.diploma.repositories.user.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    RoleRepository roleRepository;
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByUsername(username);

        if (!userEntity.getIsActive()) {
            throw new AuthorizationServiceException("User " + username + " is not active, please contact administrator for activation.");
        }

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
    }

    @PostConstruct
    public void createDefaultUsers() {
        final Role adminRole = createRoleWithName("ADMIN");
        final Role tutorRole = createRoleWithName("TUTOR");
        final Role studentRole = createRoleWithName("STUDENT");


        final Set<Role> allRoles = new HashSet<Role>() {{
            add(adminRole);
            add(tutorRole);
            add(studentRole);
        }};
        roleRepository.save(allRoles);

        final UserEntity admin = createUserWithRole("admin@gmail.com", "admin", "admin", "Адмін", "Адмінський", adminRole);
        final UserEntity tutor = createUserWithRole("tutor@gmail.com", "tutor", "tutor", "Вчитель", "Вчительський", tutorRole);
        final UserEntity student = createUserWithRole("student@gmail.com", "student", "student", "Студент", "Студентський", studentRole);
        final UserEntity student2 = createUserWithRole("student2@gmail.com", "student2", "student2", "Бакалавр", "Бакалаврський", studentRole);

        userRepository.save(admin);
        userRepository.save(tutor);
        userRepository.save(student);
        userRepository.save(student2);
    }

    private UserEntity createUserWithRole(final String email, final String password,
                                          final String username, final String name, final String surname, Role role) {
        final Set<Role> roles = new HashSet<Role>(){{
            add(role);
        }};
        return createUserWithRoles(email, password, username, name, surname, roles);
    }

    private UserEntity createUserWithRoles(final String email, final String password,
                                           final String username, final String name, final String surname, final Set<Role> allRoles) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setUsername(username);
        userEntity.setSurname(surname);
        userEntity.setName(name);
        userEntity.setIsActive(true);
        userEntity.setPassword(password);
        userEntity.setRoles(allRoles);
        return userEntity;
    }

    private Role createRoleWithName(final String name) {
        final Role role = new Role();
        role.setName(name);
        return role;
    }
}
