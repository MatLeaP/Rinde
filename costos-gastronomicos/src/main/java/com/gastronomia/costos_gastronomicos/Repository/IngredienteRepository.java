package com.gastronomia.costos_gastronomicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.Model.Ingrediente;
import java.util.Optional;



@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente , Long > {

    Optional <Ingrediente> findByNombre(String nombre);


}
