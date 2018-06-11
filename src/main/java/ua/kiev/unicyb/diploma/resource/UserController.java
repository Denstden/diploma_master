package ua.kiev.unicyb.diploma.resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ua.kiev.unicyb.diploma.domain.entity.user.Role;
import ua.kiev.unicyb.diploma.domain.entity.user.UserEntity;
import ua.kiev.unicyb.diploma.dto.request.UserDto;
import ua.kiev.unicyb.diploma.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> register(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = "role")
    @PreAuthorize("hasAuthority('TUTOR') or hasAuthority('ADMIN')")
    public Iterable<UserEntity> findByRole(@RequestParam(name = "role") String role) {
        return userService.allUsersByRole(role.toUpperCase());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<UserEntity> findAll() {
        return userService.allUsers();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<UserEntity> userInfo(@RequestHeader(name = "Authorization") String authorization) {

        final boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (isAuthenticated) {
            final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if (user != null) {
                return new ResponseEntity<>(userService.findByUsername(user.getUsername()), HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
