package ua.kiev.unicyb.diploma.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.User;
import ua.kiev.unicyb.diploma.repositories.RoleRepository;
import ua.kiev.unicyb.diploma.repositories.UserRepository;

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
        final User user = userRepository.findByUsername(username);

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
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

        final User admin = createUserWithRole("admin@gmail.com", "admin", "admin", adminRole);
        final User tutor = createUserWithRole("tutor@gmail.com", "tutor", "tutor", tutorRole);
        final User student = createUserWithRole("student@gmail.com", "student", "student", studentRole);

        userRepository.save(admin);
        userRepository.save(tutor);
        userRepository.save(student);
    }

    private User createUserWithRole(final String email, final String password,
                                    final String username, Role role) {
        final Set<Role> roles = new HashSet<Role>(){{
            add(role);
        }};
        return createUserWithRoles(email, password, username, roles);
    }

    private User createUserWithRoles(final String email, final String password,
                                     final String username, final Set<Role> allRoles) {
        final User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(allRoles);
        return user;
    }

    private Role createRoleWithName(final String name) {
        final Role role = new Role();
        role.setName(name);
        return role;
    }
}
