package com.gastronomia.costos_gastronomicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.Model.Plato;



@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    


}
