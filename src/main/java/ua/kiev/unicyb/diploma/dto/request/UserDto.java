package ua.kiev.unicyb.diploma.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Boolean isActive;
    private String email;
    private List<RoleDto> roles;
}
