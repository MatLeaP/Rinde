package com.gastronomia.costos_gastronomicos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.Model.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    @Query("SELECT p FROM Plato p WHERE p.cliente.cliente_id = :clienteId")
    List<Plato> findByClienteId(@Param("clienteId") Long clienteId);

}
