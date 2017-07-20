package com.allure.service.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Setter
@Getter
public class LoginResponse {

    private Long userId;

    private String username;

    private String role;

    private String accessToken;

    private String refreshToken;
}
