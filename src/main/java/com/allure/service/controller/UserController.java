package com.allure.service.controller;

import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.persistence.entity.User;
import com.allure.service.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<User>> list() {
        return new SuccessResponse<>(userRepository.findAll());
    }
}
