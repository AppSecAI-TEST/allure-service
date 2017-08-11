package com.allure.service.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Getter
@Setter
public class UserCreateRequest {

    @NotEmpty(message = "NotEmpty.userCreateRequest.username")
    @Pattern(regexp = "^[a-z][a-zA-Z0-9_]{3,9}$", message = "Pattern.userCreateRequest.username")
    private String username;

    @NotEmpty(message = "NotEmpty.userCreateRequest.password")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", message = "Pattern.userCreateRequest.password")
    private String password;
}
