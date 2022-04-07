package com.microservicios.seguridad.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicios.seguridad.client.entity.Usuario;



/***
 * Crud para {@link Usuario}
 * @author Hector
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	/**
	 * Sentencia para buscar usuario por email
	 * @param email
	 * @return
	 */
	Usuario findByEmail(String email);

}
