package com.gastronomia.costos_gastronomicos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gastronomia.costos_gastronomicos.Model.Proveedor;
import com.gastronomia.costos_gastronomicos.Service.ProveedorService;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<Proveedor> saveProveedor(@RequestBody Proveedor proveedor){

        Proveedor newProveedor = proveedorService.saveProveedor(proveedor);

        return new ResponseEntity<>(newProveedor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAllProveedor(){

        List<Proveedor> proveedores = proveedorService.getAllProveedores();

        return ResponseEntity.ok(proveedores);
    }


}
