package com.gastronomia.costos_gastronomicos.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gastronomia.costos_gastronomicos.DTO.RegistroClienteDTO;
import com.gastronomia.costos_gastronomicos.Model.Cliente;
import com.gastronomia.costos_gastronomicos.Service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {

        Cliente nuevoCliente = clienteService.saveCliente(cliente);

        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PostMapping("/registro-completo")
    public ResponseEntity<Cliente> crearClienteCompleto(@RequestBody RegistroClienteDTO dto) {
        // Llamamos al nuevo método del service
        Cliente nuevoCliente = clienteService.registrarClienteCompleto(dto);

        // Retornamos la empresa creada
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> traerClientes() {

        List<Cliente> clientes = clienteService.getAllClientes();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> traerCliente(@PathVariable Long id) {

        Cliente cliente = clienteService.getClienteById(id);

        return ResponseEntity.ok(cliente);
    }

}
