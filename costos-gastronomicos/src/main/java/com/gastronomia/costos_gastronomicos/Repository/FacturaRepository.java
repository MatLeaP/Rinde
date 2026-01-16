package com.gastronomia.costos_gastronomicos.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gastronomia.costos_gastronomicos.DTO.FacturaDto;
import com.gastronomia.costos_gastronomicos.Model.Factura;



@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>  {


    @Query("SELECT f FROM Factura f WHERE f.numeroFactura = :numero AND f.proveedor.proveedor_id = :id")
    Optional<Factura> findByNumeroFacturaAndProveedorId(
        @Param("numero") String numeroFactura, 
        @Param("id") Long proveedor_id   );

    

    @Query("SELECT f FROM Factura f")
    List<FacturaDto> findAllProjectedBy();

    Optional<Factura> findByNumeroFactura(String numeroFactura);



}
