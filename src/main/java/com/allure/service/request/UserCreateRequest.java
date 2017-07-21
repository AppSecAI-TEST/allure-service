package com.allure.service.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Getter
@Setter
public class UserCreateRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
