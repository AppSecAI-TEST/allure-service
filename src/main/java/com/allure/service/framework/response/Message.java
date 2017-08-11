package com.allure.service.framework.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
@Getter
@Setter
public class Message {

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
