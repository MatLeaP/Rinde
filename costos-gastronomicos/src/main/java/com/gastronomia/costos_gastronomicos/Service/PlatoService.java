package com.gastronomia.costos_gastronomicos.Service;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.Model.Plato;
import com.gastronomia.costos_gastronomicos.Repository.PlatoRepository;

@Service
public class PlatoService {

    @Autowired
    PlatoRepository platoRepository;


    public Plato savePlato(Plato plato){

        return platoRepository.save(plato);
    }

    public List<Plato> getAllPlatos(){

        return platoRepository.findAll();
                 
    }

    public Plato getPlatoById(Long id){

        Plato plato = platoRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("No se encontro el plato"));

        return plato;
    }

    public void deletePlato(Long id){

        Plato plato = platoRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("No se encontro el plato") );

        platoRepository.delete(plato);
    }

    
}
