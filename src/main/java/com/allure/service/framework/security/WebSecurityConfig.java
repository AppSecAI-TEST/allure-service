package com.allure.service.framework.security;

import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.ErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
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

}
