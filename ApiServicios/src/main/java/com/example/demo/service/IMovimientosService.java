package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.EstadoDeCuentaDTO;
import com.example.demo.dao.MovimientosDTO;

public interface IMovimientosService {

	void guardarMovimientoNuevo(MovimientosDTO movimientosDTO);
	
	String guardarMovimientosWebClient(MovimientosDTO movimientosDTO);
	
	EstadoDeCuentaDTO[] obtenerListadoEstadoCuentaByIdentificacionYFecha(String identificaion, String fechaDesde, String fechaHasta);
}
