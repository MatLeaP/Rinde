package com.gastronomia.costos_gastronomicos.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gastronomia.costos_gastronomicos.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
        
    }


    @Bean
    public PasswordEncoder PasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilita la protección CSRF (necesaria para APIs REST)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 2. Define las reglas de autorización
            .authorizeHttpRequests(authorize -> authorize
                // Permite el acceso a todos los endpoints en /api/** sin autenticación
                .requestMatchers("/api/**").permitAll() 
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                // Permite el acceso a la consola de H2 (si la estás usando)
                .requestMatchers("/h2-console/**").permitAll()
                // Cualquier otra solicitud requiere autenticación (por si acaso)
                .anyRequest().authenticated()
            )
          ;

        return http.build();
    }
}
