package com.allure.service.framework.exceprion;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MessageMethodArgumentResolver;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    private GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {ApiException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorResponse apiException(ApiException ex) {
        String code = ex.getMsgCode();
        String message = messageSource.getMessage(code, ex.getMsgArgs(), LocaleContextHolder.getLocale());
        log.error("Api Exception, Code = {}, Message = {}", code, message);
        return new ErrorResponse(code, message);
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorResponse accessDeniedException(AccessDeniedException ex) {
        log.error("Access Denied", ex);
        return new ErrorResponse(MessageCode.Global.ACCESS_DENIED,
                messageSource.getMessage(MessageCode.Global.ACCESS_DENIED, null, LocaleContextHolder.getLocale()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Argument Not Valid", ex);
        BindingResult result = ex.getBindingResult();
        final ErrorResponse<Object> response = new ErrorResponse<>();
        if (result != null && result.hasErrors()) {
            List<ObjectError> globalErrors = result.getGlobalErrors();
            if (globalErrors != null && !globalErrors.isEmpty()) {
                globalErrors.forEach(error -> {
                    response.addMessage(new BaseResponse.Message(error.getCode(), error.getDefaultMessage()));
                });
            }
            List<FieldError> fieldErrors = result.getFieldErrors();
            if (fieldErrors != null && !fieldErrors.isEmpty()) {
                fieldErrors.forEach(error -> {
                    response.addMessage(new BaseResponse.Message(error.getCode(), error.getDefaultMessage()));
                });
            }
        }
        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("internal exception", ex);
        return new ResponseEntity<>(new ErrorResponse<>(MessageCode.Global.INTERNAL_ERROR, ex.getMessage()), headers, status);
    }
}
