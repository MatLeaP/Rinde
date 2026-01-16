package com.gastronomia.costos_gastronomicos.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;

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
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long factura_id;

    private String numeroFactura;

    private LocalDate fecha;

    private double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id" , nullable = false)
    @JsonIgnoreProperties({"usuarios", "proveedores", "platos", "inventario", "facturas"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    @JsonIgnoreProperties({"ingredienteSuministrados", "facturas", "cliente"})
    private Proveedor proveedor;

    @OneToMany(mappedBy = "factura", cascade = {CascadeType.ALL})
    private List<DetalleFactura> detalle;
    

}
