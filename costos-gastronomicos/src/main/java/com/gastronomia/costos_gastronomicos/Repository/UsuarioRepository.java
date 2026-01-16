package com.gastronomia.costos_gastronomicos.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository< Usuario, Long> {


    Optional<Usuario> findByUserName(String username);
}
