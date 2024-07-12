package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CuentaDTO;
import com.example.demo.entity.Cliente;

@Service
public class CuentaService implements ICuentaService{

										  
	@Autowired
	private KafkaTemplate<String, CuentaDTO> kafkaTemplate;
	@Autowired
	private IClienteService clienteService;
	

	@Override
	public void guardarCuentaNueva(CuentaDTO cuentaDTO) {
		if(cuentaDTO.getIdentificacion() != null) {
			// Consultamos que el cliente existe
			
			Cliente cliente = clienteService.obtenerClienteByIdentificacion(cuentaDTO.getIdentificacion());
			if(cliente != null) {
				cuentaDTO.setIdCliente(cliente.getClienteId());
				//Mandamos a crear o consultar la cuenta.
				kafkaTemplate.send("cuentas", cuentaDTO);
			}
		}
		
	}
	
	
	
}
