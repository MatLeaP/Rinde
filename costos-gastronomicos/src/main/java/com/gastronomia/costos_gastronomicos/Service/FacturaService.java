package com.gastronomia.costos_gastronomicos.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gastronomia.costos_gastronomicos.Model.DetalleFactura;
import com.gastronomia.costos_gastronomicos.Model.Factura;
import com.gastronomia.costos_gastronomicos.Model.Ingrediente;
import com.gastronomia.costos_gastronomicos.Repository.FacturaRepository;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    IngredienteService ingredienteService;

    @Transactional
    public Factura saveFactura(Factura factura) {
        List<DetalleFactura> detalles = factura.getDetalle();

        if (detalles != null && !detalles.isEmpty()) {
            double totalFactura = 0;

            for (DetalleFactura detalle : detalles) {

                totalFactura += detalle.getPrecioUnitarioCompra() * detalle.getCantidadComprada();

                Ingrediente ingredienteEnviado = detalle.getIngrediente();

                if (ingredienteEnviado != null && ingredienteEnviado.getNombre() != null
                        && !ingredienteEnviado.getNombre().isEmpty()) {

                    Ingrediente ingredienteBD = ingredienteService
                            .getIngredienteByName(ingredienteEnviado.getNombre())
                            .orElse(null);

                    if (ingredienteBD != null) {

                        double stockActualizado = ingredienteBD.getStock() + detalle.getCantidadComprada();
                        double nuevoPrecio = detalle.getPrecioUnitarioCompra();

                        ingredienteBD.setPrecioUnitario(nuevoPrecio);
                        ingredienteBD.setStock(stockActualizado);

                        ingredienteBD.setPrecioUnitario(detalle.getPrecioUnitarioCompra());

                        ingredienteService.saveIngrediente(ingredienteBD);
                        detalle.setIngrediente(ingredienteBD);
                    } else {

                        ingredienteEnviado.setStock(detalle.getCantidadComprada());
                        ingredienteEnviado.setPrecioUnitario(detalle.getPrecioUnitarioCompra());

                        ingredienteEnviado.setCliente(factura.getCliente());
                        ingredienteEnviado.setProveedor(factura.getProveedor());

                        if (ingredienteEnviado.getUnidadDeMedida() == null) {
                            ingredienteEnviado.setUnidadDeMedida("Unidad");
                        }

                        Ingrediente guardado = ingredienteService.saveIngrediente(ingredienteEnviado);
                        detalle.setIngrediente(guardado);
                    }
                } else {
                    throw new RuntimeException(
                            "Error: El detalle de la factura debe incluir un objeto 'ingrediente' con un 'nombre' válido.");
                }

                detalle.setFactura(factura);
            }
            factura.setTotal(totalFactura);

        } else {
            throw new RuntimeException("Error: La factura no contiene detalles.");
        }

        return facturaRepository.save(factura);
    }

    public Factura getFacturaById(Long id) {

        return facturaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe la factura con ese id"));

    }

    public Optional<Factura> getFacturaByNumber(String number) {

        return facturaRepository.findByNumeroFactura(number);

    }

    public Optional<Factura> getFacturaByNumberAndProveedorIdAndClienteId(String number, Long proveedor_id, Long cliente_id) {
        return facturaRepository.findByIdAndProveedorIdAndClienteId(number, proveedor_id, cliente_id);
    }

    public List<Factura> getAllFacturas() {

        return facturaRepository.findAll();
    }

    public void deleteFactura(Long id) {

        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se puede obtener la factura con ese id"));

        facturaRepository.delete(factura);
    }
}

// Long idIngrediente = detalle.getIngrediente().getIngrediente_id();