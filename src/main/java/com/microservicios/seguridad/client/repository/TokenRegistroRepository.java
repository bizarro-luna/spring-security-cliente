package com.microservicios.seguridad.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.seguridad.client.entity.TokenRegistro;
/**
 * Repostorio de {@link TokenRegistro}
 * @author Hector
 *
 */
public interface TokenRegistroRepository extends JpaRepository<TokenRegistro, Long>{

	
	/**
	 * Metodo para buscar el token
	 * @param token
	 * @return
	 */
	TokenRegistro findByToken(String token);

}
