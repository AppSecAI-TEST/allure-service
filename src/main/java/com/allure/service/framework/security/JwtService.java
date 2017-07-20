package com.allure.service.framework.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.settings")
@Component
@Getter
@Setter
public class JwtService {

    private String secretKey;

    private long accessTokenExpirationSeconds;

    private String issuer;

    private long refreshTokenExpirationSeconds;

    public String createAccessToken(@NonNull UserContext context) {
        Claims claims = Jwts.claims().setSubject(context.getUsername());
        claims.put("id", context.getId());
        claims.put("username", context.getUsername());
        claims.put("role", context.getRole());
        LocalDateTime currentTime = LocalDateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(accessTokenExpirationSeconds)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String createRefreshToken(@NonNull String accessToken) {
        Claims claims = Jwts.claims().setSubject(accessToken);
        LocalDateTime currentTime = LocalDateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(refreshTokenExpirationSeconds)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public UserContext parseAccessToken(@NonNull String accessToken) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken);
        String username = jws.getBody().getSubject();
        Long id = jws.getBody().get("id", Long.class);
        String role = jws.getBody().get("role", String.class);
        UserContext context = new UserContext(id, username, role);
        return context;
    }

    public String parseRefreshToken(String refreshToken) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
        return jws.getBody().getSubject();
    }

}
