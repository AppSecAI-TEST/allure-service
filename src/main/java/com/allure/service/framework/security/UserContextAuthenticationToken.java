package com.allure.service.framework.security;

import lombok.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class UserContextAuthenticationToken extends AbstractAuthenticationToken {

    @NonNull
    private UserContext userContext;

    public UserContextAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userContext = userContext;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userContext;
    }
}
