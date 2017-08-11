package com.allure.service.framework.exceprion;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
@RestController
@Slf4j
public class ErrorResponseController extends AbstractErrorController {

    public ErrorResponseController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorResponse error(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        response.setStatus(status.value());
        return new ErrorResponse(MessageCode.Global.INTERNAL_ERROR, null);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
