package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Setter
@Getter
public class ErrorResponse<T> extends BaseResponse<T> {

    private String errorCode;

    public ErrorResponse() {
        this(null);
    }

    public ErrorResponse(String errorCode) {
        this(errorCode, null);
    }

    public ErrorResponse(String errorCode, String errorMsg) {
        super(State.Error, null, errorMsg);
        this.errorCode = errorCode;
    }
}
