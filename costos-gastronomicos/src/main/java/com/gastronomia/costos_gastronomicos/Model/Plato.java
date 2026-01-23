package com.gastronomia.costos_gastronomicos.Model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plato_id;

    private String nombre;

    private String descripcion;

    private double precioVenta;

    private double costoTotal;

    private double margen;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PlatoIngrediente> receta;


    public double calcularCosto() {
        if (this.receta == null || this.receta.isEmpty()) {
            this.costoTotal = 0;
            return 0.0;
        }
        
        this.costoTotal = receta.stream()
                .filter(item -> item.getIngrediente() != null)
                .mapToDouble(item -> {
                    // Si el ingrediente viene solo con ID del JSON, el precio será 0.
                    // Asegúrate en el Service de cargar el ingrediente completo.
                    return item.getIngrediente().getPrecioUnitario() * item.getCantidad();
                })
                .sum();
        
        return this.costoTotal;
    }

    public double aplicarMargen() {
        if (this.costoTotal > 0 && this.margen > 0) {
            this.precioVenta = this.costoTotal + (this.costoTotal * this.margen / 100);
        } else {
            this.precioVenta = this.costoTotal; // Si no hay margen, el precio es el costo
        }
        return this.precioVenta;
    }
}
