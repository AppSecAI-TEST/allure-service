package com.allure.service.framework.security;

import com.allure.service.framework.response.ErrorResponse;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.persistence.entity.User;
import com.allure.service.srv.UserService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Slf4j
public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private UserService userService;

    private JwtService jwtService;

    private ObjectMapper mapper = new ObjectMapper();
    private JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class);

    protected AjaxAuthenticationFilter() {
        super("/login");
        setAuthenticationSuccessHandler(this);
        setAuthenticationFailureHandler(this);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (StringUtils.isEmpty(request.getContentType())) {
            throw new AuthenticationServiceException("Content type not specified");
        }
        MediaType mediaType = MediaType.parseMediaType(request.getContentType());
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            throw new AuthenticationServiceException("Media type not supported! " + mediaType);
        }
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String json = builder.toString();
        if (StringUtils.isEmpty(json)) {
            throw new AuthenticationServiceException("No request body found");
        }
        HashMap<String, String> params = mapper.readValue(json, javaType);
        String userName = params.get("username");
        String password = params.get("password");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException("bad request, no username or password found!");
        }
        User user = userService.login(userName, password);
        UserContext userContext = new UserContext(user.getId(), user.getUsername(), user.getRole().getCode());
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
        return new UserContextAuthenticationToken(userContext, authorities);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("authentication failure!", exception);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(new ErrorResponse<>()));
        response.getWriter().flush();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(String.format("authentication success! user = %s", authentication.getName()));
        UserContext userContext = (UserContext) authentication.getPrincipal();
        String accessToken = jwtService.createAccessToken(userContext);
        String refreshToken = jwtService.createRefreshToken(accessToken);
        Map<String, Object> map = new HashMap<>();
        map.put("id", userContext.getId());
        map.put("username", userContext.getUsername());
        map.put("role", userContext.getRole());
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        SuccessResponse<Map<String, Object>> successResponse = new SuccessResponse<>(map);
        response.getWriter().write(mapper.writeValueAsString(successResponse));
        response.getWriter().flush();
    }
}
