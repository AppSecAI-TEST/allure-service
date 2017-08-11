package com.allure.service.srv.impl;

import com.allure.service.component.PasswordEncrypt;
import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.constants.Role;
import com.allure.service.framework.exceprion.ApiException;
import com.allure.service.persistence.entity.User;
import com.allure.service.persistence.entity.User_;
import com.allure.service.persistence.repository.UserRepository;
import com.allure.service.request.UserCreateRequest;
import com.allure.service.srv.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public User create(UserCreateRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null) throw new ApiException(MessageCode.User.USERNAME_EXIST, request.getUsername());
        user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.User);
        userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> list(int page, int pageSize, String username, String role) {
        Pageable pageable = new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "lastModifiedDate"));
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(role)) {
            return userRepository.findAll(pageable);
        }
        Specification<User> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(username))
                predicates.add(cb.like(root.get(User_.username), "%" + username + "%"));
            if (!StringUtils.isEmpty(role))
                predicates.add(cb.equal(root.get(User_.role), Role.of(role)));
            Predicate[] array = new Predicate[predicates.size()];
            return cb.or(predicates.toArray(array));
        };
        return userRepository.findAll(specification, pageable);
    }
}
