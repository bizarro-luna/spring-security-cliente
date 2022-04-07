package com.microservicios.seguridad.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.seguridad.client.entity.ReinicioPasswordToken;

public interface ReinicioPasswordTokenRepository extends JpaRepository<ReinicioPasswordToken, Long> {

	
	/**
	 * Metodo para buscar por token
	 * @param token
	 * @return
	 */
	ReinicioPasswordToken findByToken(String token);

}
