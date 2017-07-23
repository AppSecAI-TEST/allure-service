package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class SuccessResponse<T> extends BaseResponse<T> {

    public SuccessResponse() {
        this(null, null);
    }

    public SuccessResponse(String message) {
        this(null, message);
    }

    public SuccessResponse(T t) {
        this(t, null);
    }

    public SuccessResponse(T t, String message) {
        super(State.Success, t, message);
    }
}
