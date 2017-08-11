package com.allure.service.framework.controller;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.response.ErrorResponse;
import com.allure.service.framework.response.Message;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.framework.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

/**
 * Created by yang_shoulai on 8/10/2017.
 */
public abstract class BaseController {

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected <T> SuccessResponse<T> success() {
        return new SuccessResponse<>();
    }

    protected <T> SuccessResponse<T> success(T result) {
        return new SuccessResponse<>(result);
    }

    protected <T> SuccessResponse<T> success(T result, String msgCode, Object... args) {
        String message = resolveMessage(msgCode, args);
        return new SuccessResponse<>(result, new Message(msgCode, message));
    }

    protected <T> ErrorResponse<T> error() {
        return this.error(MessageCode.Global.OPERATION_ERROR);
    }

    protected <T> ErrorResponse<T> error(String msgCode, Object... args) {
        String message = resolveMessage(msgCode, args);
        return new ErrorResponse<>(msgCode, message);
    }

    protected String resolveMessage(String code, Object[] args) {
        return this.resolveMessage(code, args, null);
    }

    protected String resolveMessage(String code, Object[] args, String defaultMessage) {
        return this.messageSource.getMessage(code, args, defaultMessage, resolveLocale());
    }

    protected Locale resolveLocale() {
        return LocaleContextHolder.getLocale();
    }

    protected UserContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : (UserContext) authentication.getPrincipal();
    }

    protected boolean authenticated() {
        return getUserContext() != null;
    }

}
