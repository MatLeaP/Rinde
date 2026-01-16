package com.gastronomia.costos_gastronomicos.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.Model.Proveedor;
import com.gastronomia.costos_gastronomicos.Repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public Proveedor saveProveedor(Proveedor proveedor){
        
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> getAllProveedores(){

        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(Long id){

        Proveedor proveedor = proveedorRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("Proveedor no encontrado") );
        return proveedor;
    }

    public void deleteProveedor(Long id){

        Proveedor proveedor = proveedorRepository.findById(id)
                                .orElseThrow(() -> new NoSuchElementException("No se encontro el proveedor"));

        proveedorRepository.delete(proveedor);

    }

}
