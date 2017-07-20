package com.allure.service.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Component
public class PasswordEncrypt extends BCryptPasswordEncoder {
}
