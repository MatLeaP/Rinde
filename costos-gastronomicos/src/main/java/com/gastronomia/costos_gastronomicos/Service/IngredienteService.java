package com.gastronomia.costos_gastronomicos.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.Model.Ingrediente;
import com.gastronomia.costos_gastronomicos.Repository.IngredienteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteService {

    @Autowired
    IngredienteRepository ingredienteRepository;

    public Ingrediente saveIngrediente(Ingrediente ingrediente){
        
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> getAllIngredientes(){

        return ingredienteRepository.findAll();
    }

    public Ingrediente getIngredienteById(Long id){

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("No se pudo encontrar el ingrediente con ese id"));

        return ingrediente;
    }

    public void eliminarIngrediente(Long id){

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("No se encontro el ingrediente"));
        
        ingredienteRepository.delete(ingrediente);
    }

    @Transactional
    public Ingrediente updateIngrediente(Ingrediente newIngrediente, Long id){

        Ingrediente ingrediente =ingredienteRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("No se encontro el ingrediente"));

        ingrediente.setNombre(newIngrediente.getNombre());
        ingrediente.setPrecioUnitario(newIngrediente.getPrecioUnitario());
        ingrediente.setStock(newIngrediente.getStock());
        ingrediente.setUnidadDeMedida(newIngrediente.getUnidadDeMedida());

        return ingredienteRepository.save(ingrediente);
    }

    public Optional<Ingrediente> getIngredienteByName(String name){        

        return ingredienteRepository.findByNombre(name);


    }

}
