package com.gastronomia.costos_gastronomicos.Service;

import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gastronomia.costos_gastronomicos.Model.Cliente;
import com.gastronomia.costos_gastronomicos.Repository.ClienteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente){
        
        return clienteRepository.save(cliente);

    }

    public List<Cliente> getAllClientes(){
        
        return clienteRepository.findAll();
    }


    public Cliente getClienteById(Long id){

        Cliente cliente = clienteRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado") );

        return cliente;
    }

    @Transactional
    public Cliente updateCliente(Long id, Cliente cliente){

        Cliente clienteExistente = clienteRepository.findById(id)
                                        .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        clienteExistente.setCuit(cliente.getCuit());
        clienteExistente.setDireccion(cliente.getDireccion());        
        clienteExistente.setNombre(cliente.getNombre());
        
        

        return clienteRepository.save(clienteExistente);

    }

    public void deleteCliente(Long id){

        Cliente cliente =  clienteRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado") );

        clienteRepository.delete(cliente);
    }



}
