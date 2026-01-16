package com.gastronomia.costos_gastronomicos.Security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 🚨 CORRECCIÓN CLAVE: El valor de expiración debe ser un long (milisegundos)
    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expirateDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        // Generamos el token forzando el algoritmo HS256
        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(expirateDate)
                // 🚨 CORRECCIÓN: Especificar explícitamente el algoritmo HS256
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}