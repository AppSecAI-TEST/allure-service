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

        //没有权限
        String ACCESS_DENIED = "access_denied";
    }

    interface User {

        //用户名已经存在
        String USERNAME_EXIST = "user_exist";

        //用户名格式不正确
        String USERNAME_PATTERN = "username_pattern";

        //密码格式不正确
        String PASSWORD_PATTERN= "password_pattern";
    }
}
