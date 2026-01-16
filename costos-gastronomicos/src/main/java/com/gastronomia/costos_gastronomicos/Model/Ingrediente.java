package com.gastronomia.costos_gastronomicos.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
//@JsonIgnoreProperties({"proveedor", "cliente"})
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingrediente_id;

    private String nombre;

    private String unidadDeMedida;

    private double stock;

    private double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"usuarios", "proveedores", "platos", "inventario", "facturas"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    @JsonIgnoreProperties({"ingredienteSuministrados", "facturas", "cliente"})
    private Proveedor proveedor;

}
