package com.allure.service.framework.exceprion;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Setter
@Getter
public class ApiException extends RuntimeException {

    private String msgCode;

    private Object[] msgArgs;

    public ApiException(String msgCode, Object... msgArgs) {
        this(msgCode, msgArgs, null);
    }

    public ApiException(String msgCode) {
        this(msgCode, null, null);
    }

    public ApiException(String msgCode, Object[] msgArgs, Throwable throwable) {
        super(throwable);
        this.msgCode = msgCode;
        this.msgArgs = msgArgs;
    }
}
