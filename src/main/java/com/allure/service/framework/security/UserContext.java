package com.allure.service.framework.security;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@ToString
@Getter
public class UserContext {

    @NonNull
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String role;

    public UserContext(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
