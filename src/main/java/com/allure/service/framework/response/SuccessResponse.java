package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class SuccessResponse<T> extends BaseResponse<T> {

    public SuccessResponse() {
        super(State.Success, null);
    }

    public SuccessResponse(T t) {
        super(State.Success, t);
    }
}
