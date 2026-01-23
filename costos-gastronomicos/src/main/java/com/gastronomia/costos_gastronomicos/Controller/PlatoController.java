package com.gastronomia.costos_gastronomicos.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.gastronomia.costos_gastronomicos.Model.Plato;
import com.gastronomia.costos_gastronomicos.Service.PlatoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/plato")
public class PlatoController {

    @Autowired
    PlatoService platosService;

    @PostMapping
    public ResponseEntity<Plato> savePlato(@RequestBody Plato plato){

        Plato newPlato =  platosService.guardarPlato(plato);

        return new ResponseEntity<>(newPlato, HttpStatus.CREATED);
    }


    
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Plato>> getAllPlatos(@PathVariable Long clienteId){

        List<Plato> platos = platosService.getAllPlatosByClienteId(clienteId);

        return ResponseEntity.ok(platos);
    }

   


}
