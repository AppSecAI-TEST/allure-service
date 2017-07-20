package com.allure.service.persistence.repository;

import com.allure.service.framework.repository.BaseRepository;
import com.allure.service.persistence.entity.User;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);
}
