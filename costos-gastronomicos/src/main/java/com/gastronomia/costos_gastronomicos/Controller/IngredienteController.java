package com.gastronomia.costos_gastronomicos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gastronomia.costos_gastronomicos.Model.Ingrediente;
import com.gastronomia.costos_gastronomicos.Service.IngredienteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {


    @Autowired
    IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<Ingrediente> saveIngrediente(@RequestBody Ingrediente ingrediente){

        Ingrediente newIngrediente = ingredienteService.saveIngrediente(ingrediente);

        return new ResponseEntity<>(newIngrediente, HttpStatus.CREATED);
    }

    @GetMapping
    @RequestMapping("{cliente_id}")
    public ResponseEntity<List<Ingrediente>> getAllIngredientesByClienteId(@PathVariable Long cliente_id){

        List<Ingrediente> ingredientes = ingredienteService.getAllIngredientesByClienteId(cliente_id);

        return ResponseEntity.ok(ingredientes);
    }

    



}
