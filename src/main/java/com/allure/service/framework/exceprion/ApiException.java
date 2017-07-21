package com.allure.service.framework.exceprion;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Setter
@Getter
public class ApiException extends RuntimeException {

    private String code;

    public ApiException(String code) {
        this(code, null);
    }

    public ApiException(String code, String message) {
        this(code, message, null);
    }

    public ApiException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}
