package com.example.demo.service;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private String tokenSecret = "IDdnQRx9dQ9nfPFoUA3y";
    private long expiresAt = 80000000;

    public String generateToken(Authentication auth){
        User userObject = (User) auth.getPrincipal();
        Date exp = new Date((new Date()).getTime() + expiresAt);
        return Jwts.builder()
            .setIssuer("IRS")
            .setSubject(String.valueOf(userObject.getId()))
            .setIssuedAt(new Date())
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS256, tokenSecret).compact();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Long getTokenId(String token) {
        Claims body = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return Long.valueOf(body.getSubject());
    }
}
