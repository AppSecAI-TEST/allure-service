package com.allure.service.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Setter
@Getter
public class RefreshTokenRequest {

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;


}
