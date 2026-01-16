package com.gastronomia.costos_gastronomicos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import com.gastronomia.costos_gastronomicos.DTO.LoginDTO;
import com.gastronomia.costos_gastronomicos.DTO.TokenDTO;
import com.gastronomia.costos_gastronomicos.DTO.UserRegisterDTO;
import com.gastronomia.costos_gastronomicos.Model.Usuario;
import com.gastronomia.costos_gastronomicos.Security.JwtGenerator;
import com.gastronomia.costos_gastronomicos.Service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator; // Inyectar el componente JWT

    public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) { // 
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register") 
    public ResponseEntity<Usuario> userRegister(@RequestBody UserRegisterDTO userRegisterDTO){

        Usuario usuario = authService.registrarUsuario(userRegisterDTO);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        
        // 1. Crear un token con las credenciales (aún no autenticado)
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUserName(),
                loginDTO.getPassword()
            )
        );
        
        // 2. Establecer el usuario como autenticado en el contexto de Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // 3. Generar el JWT para devolver al cliente
        String token = jwtGenerator.generateToken(authentication);

         
        
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
