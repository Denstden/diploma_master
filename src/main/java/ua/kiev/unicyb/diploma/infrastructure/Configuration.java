package ua.kiev.unicyb.diploma.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.PostConstruct;

@org.springframework.context.annotation.Configuration
public class Configuration {

    private final ObjectMapper objectMapper;

    @Autowired
    public Configuration(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initObjectMapper() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
