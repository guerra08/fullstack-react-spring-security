package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${app.token.secret}")
    private String tokenSecret;
    @Value("${app.token.expires_ms}")
    private long tokenExpiresMs;

    @Override
    public String generateToken(Authentication auth){
        User userObject = (User) auth.getPrincipal();
        Date exp = new Date((new Date()).getTime() + tokenExpiresMs);
        return Jwts.builder()
            .setIssuer("IRS")
            .setSubject(String.valueOf(userObject.getId()))
            .setIssuedAt(new Date())
            .setExpiration(exp)
            .signWith(Keys.hmacShaKeyFor(tokenSecret.getBytes())).compact();
    }

    @Override
    public boolean isTokenValid(String token){
        try {
            var jwtParser = Jwts.parserBuilder().setSigningKey(tokenSecret.getBytes()).build();
            jwtParser.parse(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Long getTokenId(String token) {
        var jwtParser = Jwts.parserBuilder().setSigningKey(tokenSecret.getBytes()).build();
        Claims body = jwtParser.parseClaimsJws(token).getBody();
        return Long.valueOf(body.getSubject());
    }
}
