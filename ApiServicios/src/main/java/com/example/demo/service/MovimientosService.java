package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.JSONParserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dao.EstadoDeCuentaDTO;
import com.example.demo.dao.MovimientosDTO;
import com.example.demo.entity.Cliente;
import com.google.gson.JsonObject;

import reactor.core.publisher.Mono;

@Service
public class MovimientosService implements IMovimientosService {

	@Autowired
	private IClienteService clienteService;

	private WebClient webClient;

	@Autowired
	private KafkaTemplate<String, MovimientosDTO> kafkaTemplateMovimientos;

	@Override
	public void guardarMovimientoNuevo(MovimientosDTO movimientosDTO) {

		if (movimientosDTO.getIdentificacion() != null) {

			Cliente cliente = clienteService.obtenerClienteByIdentificacion(movimientosDTO.getIdentificacion());
			if (cliente != null) {

				// Mandamos a crear o consultar la cuenta.
				kafkaTemplateMovimientos.send("movimientos", movimientosDTO);
			}

		}

	}

	@Override
	public String guardarMovimientosWebClient(MovimientosDTO movimientosDTO) {
		String response = "";
		if (movimientosDTO.getIdentificacion() != null) {
			getWebClient("registrarMovimiento");
			Cliente cliente = clienteService.obtenerClienteByIdentificacion(movimientosDTO.getIdentificacion());
			if (cliente != null) {
				response = webClient.post().contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(movimientosDTO)).retrieve().bodyToMono(String.class).block();

			}
		}
		return response;

	}

	public void getWebClient(String endPoint) {
		this.webClient = WebClient.builder().baseUrl("http://127.0.0.1:8081/movimientos/" + endPoint).build();

	}

	@Override
	public EstadoDeCuentaDTO[] obtenerListadoEstadoCuentaByIdentificacionYFecha(String identificaion,
			String fechaDesde, String fechaHasta) {
		EstadoDeCuentaDTO[] estados = null;
		getWebClient("estadoDecuenta");
		JsonObject object = new JsonObject();
		object.addProperty("identificacion", identificaion);
		object.addProperty("fechaDesde", fechaDesde);
		object.addProperty("fechaHasta", fechaHasta);

		Mono<EstadoDeCuentaDTO[]> response = webClient.post().contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(object.toString())).retrieve()
				.bodyToMono(EstadoDeCuentaDTO[].class).log();
		
		if(response != null) {
			estados = response.block();
			
		}
	
		return estados;
	}

}
