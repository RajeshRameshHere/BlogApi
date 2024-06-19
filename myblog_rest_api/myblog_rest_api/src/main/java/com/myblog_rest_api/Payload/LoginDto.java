package com.myblog_rest_api.Payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;

@Data
@Getter
@Setter
public class LoginDto {

    private String usernameOrEmail;
    private String password;

}
