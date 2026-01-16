package com.gastronomia.costos_gastronomicos.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cliente_id;

    private String nombre;

    private String cuit;
    
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Proveedor> proveedores;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Plato> platos;
    
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Ingrediente> inventario;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Factura> facturas;


}