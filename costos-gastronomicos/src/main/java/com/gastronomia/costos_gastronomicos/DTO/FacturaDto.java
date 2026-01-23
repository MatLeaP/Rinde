package com.gastronomia.costos_gastronomicos.DTO;

import java.time.LocalDate;

public interface FacturaDto {

    Long getFactura_id();
    String getNumeroFactura();
    LocalDate getFecha();
    double getTotal();

    ProveedorResumen getProveedor();

    interface ProveedorResumen{
        String getnombre();
    }

}
