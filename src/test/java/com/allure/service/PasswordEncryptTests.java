package com.allure.service;

import com.allure.service.component.PasswordEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yann on 2017/7/20.
 */
@Slf4j
public class PasswordEncryptTests {

    private PasswordEncrypt passwordEncrypt = new PasswordEncrypt();


    @Test
    public void encrypt() {
        String encode = passwordEncrypt.encode("123456");
        log.debug(encode);
        Assert.assertTrue(passwordEncrypt.matches("123456", encode));
    }
}
