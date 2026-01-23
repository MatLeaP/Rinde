package com.gastronomia.costos_gastronomicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.Model.Ingrediente;

import java.util.List;
import java.util.Optional;



@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente , Long > {

    Optional <Ingrediente> findByNombre(String nombre);

    @Query("SELECT i FROM Ingrediente i WHERE i.cliente.cliente_id = :id")
    List<Ingrediente> findByClienteIdEspecial(@Param("id") Long cliente_id);



}
