package com.allure.service.framework.response;

import com.allure.service.framework.constants.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private List<Message> messages = new ArrayList<>();

    protected BaseResponse(@NonNull State state, T t, Message... messages) {
        this.state = state;
        this.result = t;
        if (messages != null && messages.length > 0) {
            this.messages.addAll(Arrays.asList(messages));
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("can not process object to json");
            throw new RuntimeException(e);
        }
    }

    public void addMessage(Message msg) {
        if (!StringUtils.isEmpty(msg)) {
            this.messages.add(msg);
        }
    }

    @Getter
    @Setter
    public static class Message {

        private String code;

        private String msg;

        public Message(String code) {
            this(code, null);
        }

        public Message(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
