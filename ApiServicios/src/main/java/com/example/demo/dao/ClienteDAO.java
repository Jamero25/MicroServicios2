package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Cliente;
									//ReactiveCrudRepository

public interface ClienteDAO extends CrudRepository<Cliente, Long>{

	
	@Query("select client from Cliente client where client.identificacion = ?1")
	Cliente bsucarClienteByIdentificacion(String identificaion);
}
