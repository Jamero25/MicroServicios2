package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ClienteDAO;
import com.example.demo.dao.ClienteDTO;
import com.example.demo.entity.Cliente;

import reactor.core.publisher.Mono;

@Service
public class ClienteService implements IClienteService{

	@Autowired
	private ClienteDAO clienteDAO;
	
	@Override
	public void guardarClienteNuevo(ClienteDTO clienteDTO) {
		
		Cliente cliente = new Cliente();
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setGenero(obtenerGeneroCliente(clienteDTO.getGenero()));
		cliente.setIdentificacion(clienteDTO.getIdentificacion());
		cliente.setDireccion(clienteDTO.getDireccion());
		cliente.setTelefono(clienteDTO.getTelefono());
		cliente.setContrasenha(clienteDTO.getContrasenha());
		cliente.setClienteId(Long.valueOf(clienteDTO.getIdentificacion()));
		cliente.setEstado(clienteDTO.getEstado());
		clienteDAO.save(cliente);
	}

	
	private int obtenerGeneroCliente(String genero) {
		int gender = 0;
		switch (genero) {
		case "M": 
			gender = 1;
			break;
		case "F":
			gender = 2;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + genero);
		}
		return gender;
	}


	@Override
	public Cliente obtenerclientById(Long id) {
		return clienteDAO.findById(id).get();
	}


	@Override
	public Cliente actualizarClienteById(ClienteDTO clienteDTO, Long id) {
		Cliente cliente = clienteDAO.findById(id).get();
		if(cliente != null) {
			if(clienteDTO.getNombre() != null && !clienteDTO.getNombre().isEmpty()) {
				cliente.setNombre(clienteDTO.getNombre());
			}
			if(clienteDTO.getContrasenha() != null && !clienteDTO.getContrasenha().isEmpty()) {
				cliente.setContrasenha(clienteDTO.getContrasenha());
			}if(clienteDTO.getDireccion() != null && !clienteDTO.getDireccion().isEmpty()){
				cliente.setDireccion(clienteDTO.getDireccion());
			}if(clienteDTO.getTelefono() != null && !clienteDTO.getTelefono().isEmpty()) {
				cliente.setTelefono(clienteDTO.getTelefono());
			}if(clienteDTO.getEstado() != null) {
				cliente.setEstado(clienteDTO.getEstado());
			}
			clienteDAO.save(cliente);
		}
		return cliente;
	}


	@Override
	public void eliminarClienteById(Long id) {
		clienteDAO.deleteById(id);
		
	}


	@Override
	public Cliente obtenerClienteByIdentificacion(String identificaion) {
		return clienteDAO.bsucarClienteByIdentificacion(identificaion);
	}
}
