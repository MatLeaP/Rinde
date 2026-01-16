package com.gastronomia.costos_gastronomicos.Model;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatoIngrediente> receta = new ArrayList<>();


    public void calcularCosto(){
        if(this.receta == null || this.receta.isEmpty()){
            this.costoTotal = 0;
            return;
        }
        this.costoTotal = receta.stream()
                    .filter(item -> item.getIngrediente() != null)
                    .mapToDouble(item -> item.getIngrediente().getPrecioUnitario() * item.getCantidad())                     
                    .sum(); 
    }
}
