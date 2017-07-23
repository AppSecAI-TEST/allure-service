package com.allure.service.component;

import com.allure.service.framework.security.UserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by yann on 2017/7/22.
 */
@Component(value = "auditingAwareProvider")
public class AuditingAwareProvider implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return null;
        Authentication authentication = context.getAuthentication();
        UserContext user = (UserContext) authentication.getPrincipal();
        if (user == null) return null;
        return user.getUsername();
    }
}
