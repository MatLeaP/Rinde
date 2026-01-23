package com.gastronomia.costos_gastronomicos.Security;

import com.gastronomia.costos_gastronomicos.Model.Usuario;
import com.gastronomia.costos_gastronomicos.Repository.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencia (solo el Repositorio)
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     * Este método es llamado automáticamente por Spring Security durante el login.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Cargar el usuario desde la base de datos usando el repositorio
        Usuario usuario = usuarioRepository.findByUserName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado en la base de datos: " + username));

        // Construir y devolver un objeto UserDetails de Spring Security.
        // Spring Security usará la contraseña hasheada (usuario.getPassword()) para
        // compararla
        // con la contraseña enviada por el usuario (tras ser hasheada por el
        // PasswordEncoder).
        return new User(
                usuario.getUserName(),
                usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol())));
    }
}