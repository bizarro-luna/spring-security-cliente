package com.microservicios.seguridad.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HolaControlador {
	
	/**
	 * metodo de prueba
	 * @return
	 */
	@GetMapping("/hola")
	public String registrar() {
		return "Hola Bienvenido Perro";
	}
	

}
