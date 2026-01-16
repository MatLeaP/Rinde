package com.gastronomia.costos_gastronomicos.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detalleFactura_id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    @JsonIgnore
    private Factura factura;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;

    private double cantidadComprada;

    private double precioUnitarioCompra;

}
