//package ua.kiev.unicyb.diploma.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
////@Component
//@Slf4j
//public class DatabaseAuthenticationProvider implements AuthenticationProvider {
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials()
//                .toString();
//        log.debug("Authenticate user {}", username);
//        return new UsernamePasswordAuthenticationToken
//                (username, password, Collections.emptyList());
//
//        /*if ("externaluser".equals(username) && "pass".equals(password)) {
//
//        } else {
//            throw new
//                    BadCredentialsException("External system authentication failed");
//        }*/
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
