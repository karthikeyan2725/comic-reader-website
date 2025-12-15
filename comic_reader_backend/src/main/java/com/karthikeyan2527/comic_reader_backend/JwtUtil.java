package com.karthikeyan2527.comic_reader_backend;

import com.karthikeyan2527.comic_reader_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${web.jwt-secret-key}")
    String secretKey;

    public Integer getUserIdFromToken(String token){
        byte[] secret = Base64.getDecoder().decode(secretKey);
        Claims result = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret)).build().parseSignedClaims(token).getPayload();
        Integer userId = Integer.valueOf(result.getSubject());

        return userId;
    }

    public String generateToken(User user){
        Instant instant = Instant.now();
        byte[] secret = Base64.getDecoder().decode(secretKey);
        String token = Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .issuedAt(Date.from(instant))
                .expiration(Date.from(instant.plus(1, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();

        return token;
    }
}