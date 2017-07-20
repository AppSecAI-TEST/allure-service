package com.allure.service.persistence.entity;

import com.allure.service.framework.constants.Role;
import com.allure.service.framework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}