package com.allure.service.controller;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.ErrorResponse;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.framework.security.JwtService;
import com.allure.service.persistence.entity.User;
import com.allure.service.request.LoginRequest;
import com.allure.service.request.RefreshTokenRequest;
import com.allure.service.response.LoginResponse;
import com.allure.service.response.RefreshTokenResponse;
import com.allure.service.srv.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@RestController
@RequestMapping(value = "/token")
public class TokenController {

    private final UserService userService;

    private final JwtService jwtService;

    public TokenController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getId());
            response.setRole(user.getRole().getCode());
            response.setUsername(user.getUsername());
            String accessToken = jwtService.createAccessToken(user.getId(), user.getUsername(), user.getRole().getCode());
            response.setAccessToken(accessToken);
            response.setRefreshToken(jwtService.createRefreshToken(user.getUsername(), accessToken));
            return new SuccessResponse<>(response);
        } catch (BadCredentialsException e) {
            return new ErrorResponse<>(MessageCode.Global.BAD_CREDENTIALS, "bad credentials");
        } catch (UsernameNotFoundException e) {
            return new ErrorResponse<>(MessageCode.Global.USERNAME_NOT_FOUND, "username not found");
        }
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            Pair<String, String> pair = jwtService.parseRefreshToken(request.getRefreshToken());
            if (StringUtils.isEmpty(pair.getFirst()) || !request.getAccessToken().equals(pair.getSecond())) {
                return new ErrorResponse<>(MessageCode.Global.INVALID_TOKEN, "invalid token");
            }
            User user = userService.findByUsername(pair.getFirst());
            if (user == null) {
                return new ErrorResponse<>(MessageCode.Global.USERNAME_NOT_FOUND, "username not found");
            }
            RefreshTokenResponse response = new RefreshTokenResponse();
            String accessToken = jwtService.createAccessToken(user.getId(), user.getUsername(), user.getRole().getCode());
            response.setAccessToken(accessToken);
            response.setRefreshToken(jwtService.createRefreshToken(user.getUsername(), accessToken));
            return new SuccessResponse<>(response);
        } catch (JwtException e) {
            return new ErrorResponse<>(MessageCode.Global.INVALID_TOKEN, "invalid token");
        }
    }
}
