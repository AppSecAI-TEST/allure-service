package com.allure.service.srv;

import com.allure.service.persistence.entity.User;
import com.allure.service.request.UserCreateRequest;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public interface UserService {

    User login(@NonNull String username, @NonNull String password) throws UsernameNotFoundException, BadCredentialsException;

    User findByUsername(String username);

    User create(UserCreateRequest request);

    Page<User> list(int page, int pageSize, String username, String role);

}
