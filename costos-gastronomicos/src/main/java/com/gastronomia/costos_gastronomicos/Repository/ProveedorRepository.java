package com.gastronomia.costos_gastronomicos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.gastronomia.costos_gastronomicos.Model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("SELECT p FROM Proveedor p WHERE p.cliente.cliente_id = :clienteId")
    List<Proveedor> findByClienteId(@Param("clienteId") Long clienteId);

}
