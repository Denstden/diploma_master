package ua.kiev.unicyb.diploma.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDto {
    private String userName;
    private String password;
    private String email;
}
