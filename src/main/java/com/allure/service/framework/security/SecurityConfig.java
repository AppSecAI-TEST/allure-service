package com.allure.service.framework.security;

import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.ErrorResponse;
import com.allure.service.srv.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;

    private final UserService userService;

    private static final RequestMatcher[] SKIP_MATCHER_LIST = new RequestMatcher[]{
            new AntPathRequestMatcher("/token", "POST"),
            new AntPathRequestMatcher("/token/refresh", "POST"),
            new AntPathRequestMatcher("/users", "POST")
    };

    private static final RequestMatcher SKIP_MATCHER = request -> {
        for (RequestMatcher matcher : SKIP_MATCHER_LIST) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    };

    @Autowired
    public SecurityConfig(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        source.registerCorsConfiguration("/**", config);
        http.cors().configurationSource(source);

        http.authorizeRequests()
                .requestMatchers(SKIP_MATCHER).permitAll();

        //http.addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.headers().xssProtection().xssProtectionEnabled(true).block(true);
    }


    //用户未登录
    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            writeResponse(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse());
        };
    }

    private void writeResponse(HttpServletResponse response, int httpStatus, BaseResponse apiResponse) throws IOException {
        response.setStatus(httpStatus);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(apiResponse == null ? "" : apiResponse.toJson());
        response.getWriter().flush();
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setJwtService(jwtService);
        filter.setUserService(userService);
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(SKIP_MATCHER);
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setJwtService(jwtService);
        return filter;
    }
}
