package com.example.demo.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ClienteDTO;
import com.example.demo.dao.CuentaDTO;
import com.example.demo.dao.EstadoDeCuentaDTO;
import com.example.demo.dao.MovimientosDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.service.IClienteService;
import com.example.demo.service.ICuentaService;
import com.example.demo.service.IMovimientosService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ClientesWebService {

	@Autowired
	private IClienteService clienteService;
	@Autowired
	private ICuentaService cuentaService;
	@Autowired
	private IMovimientosService movimientosService;

	@PostMapping("/clientes")
	public String registrarCliente(@RequestBody ClienteDTO clienteDTO) {
		String respuesta = "";
		if (clienteDTO.getIdentificacion() != null) {

			System.out.println("IDEN" + clienteDTO.getIdentificacion());
			// Validamos que el cliente ya no se encuentre en bdd

			clienteService.guardarClienteNuevo(clienteDTO);
			respuesta = "Cliente registrado con éxito";

		}
		return respuesta;
	}

	@GetMapping("/clientes/{id}")
	public Cliente consultarClienteById(@PathVariable Long id) {
		return clienteService.obtenerclientById(id);
	}

	@PutMapping("/clientes/{id}")
	public Cliente actualizarClienteById(@RequestBody ClienteDTO clienteDTO, @PathVariable("id") Long id) {
		return clienteService.actualizarClienteById(clienteDTO, id);
	}

	@DeleteMapping("/clientes/{id}")
	public String borrarRegistroClienteById(@PathVariable("id") Long id) {
		clienteService.eliminarClienteById(id);
		return "Registro borrado con éxito";
	}

	// Metodos para cuenta

	@PostMapping("/cuentas")
	public String registrarCuenta(@RequestBody CuentaDTO cuentaDTO) {
		String respuesta = "";
		if (cuentaDTO.getNumCuenta() != null) {

			System.out.println("Nueva cuenta" + cuentaDTO.getNumCuenta());
			// Validamos que el cliente ya no se encuentre en bdd

			cuentaService.guardarCuentaNueva(cuentaDTO);
			respuesta = "Cuenta registrado con éxito";

		}
		return respuesta;
	}

	@PostMapping("/movimientos")
	public String registrarMovimientos(@RequestBody MovimientosDTO movimientosDTO) {
		String respuesta = "";
		if (movimientosDTO.getCuenta() != null) {
			respuesta = movimientosService.guardarMovimientosWebClient(movimientosDTO);

		}
		return respuesta;
	}

	@GetMapping("/reportes/cliente={identificacion}/fecha/{rangoFechas}")
	public EstadoDeCuentaDTO[] obtenerReportes(@PathVariable("identificacion") String identificacion,
			@PathVariable("rangoFechas") String rangoFecha) {
		EstadoDeCuentaDTO[] listado = null;
		if (identificacion != null && rangoFecha != null) {
			String[] fechas = rangoFecha.split("-");
			listado = movimientosService.obtenerListadoEstadoCuentaByIdentificacionYFecha(identificacion, fechas[0],
					fechas[1]);

		}
		return listado;
	}

}
