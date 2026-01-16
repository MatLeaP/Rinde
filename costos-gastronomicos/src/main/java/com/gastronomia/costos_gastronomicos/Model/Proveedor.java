package com.gastronomia.costos_gastronomicos.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proveedor_id;

    private String nombre;

    private String cuit;

    private String telefono;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties({"usuarios", "proveedores", "platos", "inventario", "facturas"})
    private Cliente cliente;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    private List<Factura> facturas;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnoreProperties("proveedor")
    private List<Ingrediente> ingredienteSuministrados;

}
