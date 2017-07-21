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
import org.springframework.data.util.Pair;
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

    public String createAccessToken(Long userId, String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("username", username);
        claims.put("role", role);
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

    public String createRefreshToken(String username, String accessToken) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("accessToken", accessToken);
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
        Long id = Long.valueOf(jws.getBody().get("id", Integer.class));
        String role = jws.getBody().get("role", String.class);
        return new UserContext(id, username, role);
    }

    public Pair<String, String> parseRefreshToken(String refreshToken) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
        String username = jws.getBody().getSubject();
        String accessToken = jws.getBody().get("accessToken", String.class);
        return Pair.of(username, accessToken);
    }

}
