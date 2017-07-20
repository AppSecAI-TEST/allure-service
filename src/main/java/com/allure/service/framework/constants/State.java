package com.allure.service.framework.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public enum State {

    Success("0"), Error("1");

    private String code;

    State(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public static State of(String code) {
        if (Success.code.equals(code)) return Success;
        if (Error.code.equals(code)) return Error;
        return null;
    }
}
