package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class SuccessResponse<T> extends BaseResponse<T> {

    public SuccessResponse() {
        this(null, new Message[0]);
    }

    public SuccessResponse(T t) {
        this(t, new Message[0]);
    }


    public SuccessResponse(T t, Message... messages) {
        super(State.Success, t, messages);
    }
}
