package com.allure.service.framework.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by yang_shoulai on 7/21/2017.
 */
@Slf4j
@Component
public class MethodPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        UserContext user = getUserContext(authentication);
        if (user != null) {
            log.debug(String.format("check whether user [%s] has permission [%s] on domain object [%s]", user.getUsername(), permission, targetDomainObject));
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserContext user = getUserContext(authentication);
        if (user != null) {
            log.debug(String.format("check whether user [%s] has permission [%s] on target type [%s] with target id [%s]", user.getUsername(), permission, targetType, targetId));
        }
        return false;
    }


    private UserContext getUserContext(Authentication authentication) {
        return (UserContext) authentication.getPrincipal();
    }

}
