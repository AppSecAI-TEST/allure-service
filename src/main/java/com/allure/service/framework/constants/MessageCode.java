package com.allure.service.framework.constants;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
public interface MessageCode {

    interface Global {

        //客户端请求参数不正确
        String BAD_REQUEST = "bad_request";

        //内部请求处理器错误
        String INTERNAL_ERROR = "internal_error";

        //非法的令牌
        String INVALID_TOKEN = "invalid_token";

        //密码/令牌错误
        String BAD_CREDENTIALS = "bad_credentials";
        //用户名不存在
        String USERNAME_NOT_FOUND = "username_not_found";
    }

    interface User {

        //用户名已经存在
        String USERNAME_EXIST = "user_exist";
    }
}
