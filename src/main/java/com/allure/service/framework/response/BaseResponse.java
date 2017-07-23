package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Slf4j
@Getter
@Setter
public abstract class BaseResponse<T> {

    @NonNull
    private State state;

    private T result;

    private String message;

    protected BaseResponse(@NonNull State state, T t, String message) {
        this.state = state;
        this.result = t;
        this.message = message;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("can not process object to json");
            throw new RuntimeException(e);
        }
    }
}
