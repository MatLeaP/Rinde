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
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<Usuario> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {

        Usuario usuario = authService.registrarUsuario(userRegisterDTO);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("Intentando autenticar a: " + loginDTO.getUserName());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(); // Esto te dirá el error exacto en la consola de IntelliJ/Eclipse
            return new ResponseEntity<>("Error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
