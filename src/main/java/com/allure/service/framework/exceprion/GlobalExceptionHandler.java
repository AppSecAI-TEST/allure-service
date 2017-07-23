package com.allure.service.framework.exceprion;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.Access;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorResponse apiException(ApiException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("api exception occurs! Code = {}", code);
        return new ErrorResponse(code, message);
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public ErrorResponse accessDeniedException(AccessDeniedException ex) {
        log.error("access denied", ex);
        return new ErrorResponse(MessageCode.Global.ACCESS_DENIED, "access denied");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("argument not valid", ex);
        BindingResult result = ex.getBindingResult();
        ErrorResponse<Object> response = new ErrorResponse<>();
        if (result != null && result.hasErrors()) {
            response = new ErrorResponse<>(MessageCode.Global.BAD_REQUEST, "bad request");
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
