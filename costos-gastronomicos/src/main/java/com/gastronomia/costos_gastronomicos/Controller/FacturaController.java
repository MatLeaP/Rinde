package com.gastronomia.costos_gastronomicos.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gastronomia.costos_gastronomicos.DTO.FacturaDto;
import com.gastronomia.costos_gastronomicos.Model.Factura;
import com.gastronomia.costos_gastronomicos.Repository.FacturaRepository;
import com.gastronomia.costos_gastronomicos.Service.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    @Autowired
    FacturaRepository facturaRepository;

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {

        facturaService.saveFactura(factura);

        return new ResponseEntity<>(factura, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FacturaDto>> getAllFacturas() {

        List<FacturaDto> facturas = facturaRepository.findAllProjectedBy();
        return new ResponseEntity<>(facturas, HttpStatus.OK);

    }

    @GetMapping("/{number}")
    public ResponseEntity<Optional<Factura>> getFacturaByNumber(@PathVariable String number) {
        Optional<Factura> factura = facturaService.getFacturaByNumber(number);

        return ResponseEntity.ok(factura);

    }

    @GetMapping("/{number}/{id}")
    public ResponseEntity<Optional<Factura>> getFacturaByNumberAndProveedorId(@PathVariable String number,
            @PathVariable Long id) {

        Optional<Factura> factura = facturaService.getFacturaByNumberAndProveedorId(number, id);
        return ResponseEntity.ok(factura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {

        facturaService.deleteFactura(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
