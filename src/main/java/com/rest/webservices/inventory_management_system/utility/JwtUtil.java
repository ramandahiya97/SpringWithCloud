package com.rest.webservices.inventory_management_system.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key;
    public JwtUtil(@Value("${jwt.secret}")String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    //token expire
    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
    // validate token
    public boolean validateToken(String token, String username){
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
    //extract username
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    //extract claims
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
