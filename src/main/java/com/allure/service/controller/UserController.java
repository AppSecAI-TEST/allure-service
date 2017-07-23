package com.allure.service.controller;

import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.persistence.entity.User;
import com.allure.service.request.UserCreateRequest;
import com.allure.service.srv.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Page<User>> list(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                         @RequestParam(value = "username", required = false, defaultValue = "") String username,
                                         @RequestParam(value = "role", required = false, defaultValue = "") String role) {
        return new SuccessResponse<>(userService.list(page, pageSize, username, role));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse create(@Valid @RequestBody UserCreateRequest request) {
        userService.create(request);
        return new SuccessResponse("register success");
    }
}
