package com.allure.service.framework.constants;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
public interface MessageCode {

    interface Global {

        //操作成功
        String OPERATION_SUCCESS = "global.operation_success";

        //操作失败
        String OPERATION_ERROR = "global.operation_error";

        //客户端请求参数不正确
        String BAD_REQUEST = "global.bad_request";

        //内部请求处理器错误
        String INTERNAL_ERROR = "global.internal_error";

        //非法的令牌
        String INVALID_TOKEN = "global.invalid_token";

        //密码/令牌错误
        String BAD_CREDENTIALS = "global.bad_credentials";

        //用户名不存在
        String USERNAME_NOT_FOUND = "global.username_not_found";

        //没有权限
        String ACCESS_DENIED = "global.access_denied";
    }

    interface User {

        //用户名已经存在
        String USERNAME_EXIST = "user.username_exist";

        //用户名格式不正确
        String USERNAME_PATTERN = "user.username_invalid";

        //密码格式不正确
        String PASSWORD_PATTERN = "user.password_invalid";
    }

    interface Category {

        //分类编号已经存在
        String CODE_EXIST = "category.code_exist";

        //分类名称已经存在
        String LABEL_EXIST = "category.label_exist";

        //分类不存在
        String NOT_FOUND = "category.not_found";

        //分类已经被使用
        String IN_USE = "category.in_use";
    }
}
