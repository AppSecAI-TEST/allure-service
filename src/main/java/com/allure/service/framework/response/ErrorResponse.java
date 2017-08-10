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

    public ErrorResponse() {
        this(new Message[0]);
    }

    public ErrorResponse(String errorCode, String msg) {
        this(new Message(errorCode, msg));
    }

    public ErrorResponse(Message... messages) {
        super(State.Error, null, messages);
    }
}
