package com.allure.service.framework.security;

import com.allure.service.component.ApplicationContextHolder;
import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.constants.Role;
import com.allure.service.framework.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final RequestMatcher PROTECT_MATCHER = new AntPathRequestMatcher("/**");

    private static final String AUTH_HEADER = "x-auth-token";

    private static final String BEARER = "Bearer ";

    private JwtService jwtService;

    private static final RequestMatcher TOKEN_CREATE_MATCHER = new AntPathRequestMatcher("/token", "POST");

    private static final RequestMatcher TOKEN_REFRESH_MATCHER = new AntPathRequestMatcher("/token/refresh", "POST");

    private static final RequestMatcher REGISTER_MATCHER = new AntPathRequestMatcher("/users", "POST");

    protected JwtAuthenticationFilter() {
        super(request -> !TOKEN_CREATE_MATCHER.matches(request) && !TOKEN_REFRESH_MATCHER.matches(request) && PROTECT_MATCHER.matches(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(AUTH_HEADER);
        if (!REGISTER_MATCHER.matches(request) && (StringUtils.isEmpty(token) || !token.startsWith(BEARER)))
            throw new BadCredentialsException("bad token");
        if (REGISTER_MATCHER.matches(request) && (StringUtils.isEmpty(token) || !token.startsWith(BEARER))) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(Role.Anonymous.getCode()));
            return new AnonymousAuthenticationToken(request.getRemoteHost(), request.getRemoteHost(), authorities);
        }
        try {
            token = token.substring(BEARER.length());
            if (this.jwtService == null) setJwtService(ApplicationContextHolder.getBean(JwtService.class));
            UserContext userContext = jwtService.parseAccessToken(token);
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(userContext.getRole()));
            return new UserContextAuthenticationToken(userContext, authorities);
        } catch (JwtException e) {
            throw new BadCredentialsException("bad token");
        }
    }

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ErrorResponse(MessageCode.Global.INVALID_TOKEN, failed.getMessage()).toJson());
        response.getWriter().flush();
    }
}
