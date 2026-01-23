package com.gastronomia.costos_gastronomicos.Service;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.Model.Ingrediente;
import com.gastronomia.costos_gastronomicos.Model.Plato;
import com.gastronomia.costos_gastronomicos.Model.PlatoIngrediente;
import com.gastronomia.costos_gastronomicos.Repository.IngredienteRepository;
import com.gastronomia.costos_gastronomicos.Repository.PlatoRepository;

import jakarta.transaction.Transactional;

@Service
public class PlatoService {

    @Autowired
    PlatoRepository platoRepository;

    @Autowired
    IngredienteRepository ingredienteRepository;


    @Transactional
    public Plato guardarPlato(Plato plato) {
        if (plato.getReceta() != null) {
            for (PlatoIngrediente item : plato.getReceta()) {
        
                item.setPlato(plato);

        
                if (item.getIngrediente() != null && item.getIngrediente().getIngrediente_id() != null) {
        
                    Ingrediente ingredienteReal = ingredienteRepository.findById(item.getIngrediente().getIngrediente_id())
                            .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado: " + item.getIngrediente().getIngrediente_id()));
                    
        
                    item.setIngrediente(ingredienteReal);
                }
            }
        }

        
        double costoCalculado = plato.calcularCosto();
        double margen = plato.aplicarMargen();
        plato.setCostoTotal(costoCalculado);
        plato.setPrecioVenta(margen);

        
        return platoRepository.save(plato);
    }
    public List<Plato> getAllPlatosByClienteId(Long clienteId){

        return platoRepository.findByClienteId(clienteId);
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
