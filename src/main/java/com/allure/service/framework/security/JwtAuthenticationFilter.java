package com.allure.service.framework.security;

import com.allure.service.framework.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
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

    protected JwtAuthenticationFilter(RequestMatcher skipMatcher) {
        super(request -> {
            if (skipMatcher != null) {
                return !skipMatcher.matches(request) && PROTECT_MATCHER.matches(request);
            }
            return PROTECT_MATCHER.matches(request);
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(AUTH_HEADER);
        if (StringUtils.isEmpty(token) || !token.startsWith(BEARER)) throw new BadCredentialsException("bad token");
        try {
            token = token.substring(BEARER.length());
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
        response.getWriter().write(new ErrorResponse().toJson());
        response.getWriter().flush();
    }
}
