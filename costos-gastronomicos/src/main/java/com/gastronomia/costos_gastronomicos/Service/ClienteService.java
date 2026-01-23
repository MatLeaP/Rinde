package com.gastronomia.costos_gastronomicos.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.DTO.RegistroClienteDTO;
import com.gastronomia.costos_gastronomicos.Model.Cliente;
import com.gastronomia.costos_gastronomicos.Model.Usuario;
import com.gastronomia.costos_gastronomicos.Repository.ClienteRepository;
import com.gastronomia.costos_gastronomicos.Repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UsuarioRepository usuarioRepository;

    

    @Transactional
    public Cliente registrarClienteCompleto(RegistroClienteDTO dto) {
        // 1. VALIDACIÓN PREVIA: Verificar si el nombre de usuario ya está tomado
        // Lo hacemos antes de crear el cliente para no generar IDs innecesarios
        if (usuarioRepository.findByUserName(dto.getAdminUsername()).isPresent()) {
            throw new RuntimeException(
                    "Error: El nombre de usuario '" + dto.getAdminUsername() + "' ya existe. Elija otro.");
        }

        // 2. Creamos y guardamos la entidad Cliente (Empresa)
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombreEmpresa());
        cliente.setCuit(dto.getCuit());
        cliente.setDireccion(dto.getDireccion());

        // Guardamos para obtener el ID generado
        Cliente nuevoCliente = clienteRepository.save(cliente);

        // 3. Preparamos la entidad Usuario Administrador
        Usuario admin = new Usuario();
        admin.setUserName(dto.getAdminUsername());
        admin.setPassword(dto.getAdminPassword());
        admin.setRol("ADMIN_CLIENTE");
        admin.setCliente(nuevoCliente);

        // 4. Delegamos al AuthService el guardado (que también tiene su validación por
        // seguridad)
        authService.registrarUsuarioDesdeAdmin(admin);

        return nuevoCliente;
    }

    public Cliente saveCliente(Cliente cliente) {

        return clienteRepository.save(cliente);

    }

    public List<Cliente> getAllClientes() {

        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        return cliente;
    }

    @Transactional
    public Cliente updateCliente(Long id, Cliente cliente) {

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        clienteExistente.setCuit(cliente.getCuit());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setNombre(cliente.getNombre());

        return clienteRepository.save(clienteExistente);

    }

    public void deleteCliente(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        clienteRepository.delete(cliente);
    }

}
