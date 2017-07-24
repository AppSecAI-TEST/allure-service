package com.allure.service.framework.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public enum Role {

    Admin("ROLE_ADMIN"), User("ROLE_USER"), Anonymous("ROLE_ANONYMOUS");

    private String code;

    Role(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public static Role of(String code) {
        if (Admin.code.equals(code)) return Admin;
        if (User.code.equals(code)) return User;
        if (Anonymous.code.equals(code)) return Anonymous;
        return null;
    }
}
