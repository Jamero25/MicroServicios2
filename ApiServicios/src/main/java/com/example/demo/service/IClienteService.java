package com.example.demo.service;

import com.example.demo.dao.ClienteDTO;
import com.example.demo.entity.Cliente;


public interface IClienteService {

	void guardarClienteNuevo(ClienteDTO clienteDTO);

	Cliente obtenerclientById(Long id);
	
	Cliente actualizarClienteById(ClienteDTO clienteDTO, Long id);
	
	void eliminarClienteById(Long id);
	
	Cliente obtenerClienteByIdentificacion(String identificaion);
}
