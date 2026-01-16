package com.gastronomia.costos_gastronomicos.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 1. Obtiene el JWT de la cabecera "Authorization"
    private String obtenerJwtDeLaPeticion(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        // Verifica si existe el prefijo "Bearer " y lo elimina.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String token = obtenerJwtDeLaPeticion(request);

        // 2. Procesa y autentica si hay un token válido
        if (token != null) {
            try {
                String username = jwtGenerator.getUsernameFromJwt(token);
                
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                
                // Crea el objeto de autenticación de Spring Security
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, // Contraseña se establece como null después de la autenticación inicial
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            } catch (Exception e) {
                // Si el token es inválido o expiró, Spring Security manejará la excepción
                // No establecemos la autenticación y el flujo continua a los endpoints protegidos.
                System.err.println("Error al procesar el token JWT: " + e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }
}