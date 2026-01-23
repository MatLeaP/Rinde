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

        // Extraemos el rol (authority) del objeto authentication
        // Asumiendo que el usuario tiene un solo rol como en tu entidad Usuario
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        Date currentDate = new Date();
        Date expirateDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userName)
                .claim("rol", role) // 👈 AGREGAMOS ESTO: El frontend ahora podrá leer el rol
                .setIssuedAt(currentDate)
                .setExpiration(expirateDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
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