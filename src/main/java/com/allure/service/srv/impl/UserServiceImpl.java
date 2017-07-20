package com.allure.service.srv.impl;

import com.allure.service.component.PasswordEncrypt;
import com.allure.service.persistence.entity.User;
import com.allure.service.persistence.repository.UserRepository;
import com.allure.service.srv.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncrypt passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncrypt passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(@NonNull String username, @NonNull String password) throws UsernameNotFoundException, BadCredentialsException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("username %s not found", username));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("bad credentials");
        return user;
    }
}
