package com.gastronomia.costos_gastronomicos.Service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import com.gastronomia.costos_gastronomicos.DTO.LoginDTO;
import com.gastronomia.costos_gastronomicos.DTO.TokenDTO;
import com.gastronomia.costos_gastronomicos.DTO.UserRegisterDTO;
import com.gastronomia.costos_gastronomicos.Model.Cliente;
import com.gastronomia.costos_gastronomicos.Model.Usuario;
import com.gastronomia.costos_gastronomicos.Repository.ClienteRepository;
import com.gastronomia.costos_gastronomicos.Repository.UsuarioRepository;
import com.gastronomia.costos_gastronomicos.Security.JwtGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
            JwtGenerator jwtGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
    }

    public TokenDTO login(LoginDTO loginDTO) {

        try {
            // 🚨 CORRECCIÓN CLAVE: Pasar el username y password como strings
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            return new TokenDTO(token);

        } catch (AuthenticationException e) {
            // Captura las excepciones de autenticación (BadCredentials, Disabled, etc.)
            // Puedes loguear el error o relanzar una excepción personalizada:
            System.err.println("Fallo de autenticación: " + e.getMessage());

            // Relanzar la excepción. El @ControllerAdvice la puede capturar y devolver 401.
            // Si la relanzas, aparecerá el error específico en la consola.
            throw e;
        }
    }

    public Usuario registrarUsuario(UserRegisterDTO userRegisterDTO) {
        // 1. PRIMERO validamos que el nombre no exista para evitar duplicados
        if (usuarioRepository.findByUserName(userRegisterDTO.getUserName()).isPresent()) {
            throw new RuntimeException("El nombre de usuario '" + userRegisterDTO.getUserName() + "' ya está en uso.");
        }

        Cliente cliente = clienteRepository.findById(userRegisterDTO.getClienteId())
                .orElseThrow(() -> new NoSuchElementException("El cliente no existe"));

        Usuario usuario = new Usuario();
        usuario.setCliente(cliente);
        usuario.setUserName(userRegisterDTO.getUserName());
        usuario.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        usuario.setRol(userRegisterDTO.getRol());

        return usuarioRepository.save(usuario);
    }

    public void registrarUsuarioDesdeAdmin(Usuario usuario) {
        // Encriptamos la clave antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardamos el usuario (que ya trae el cliente_id asignado desde el service de
        // Cliente)
        usuarioRepository.save(usuario);
    }
}
