package com.microservicios.seguridad.client.service;

import java.util.Optional;

import com.microservicios.seguridad.client.dto.UsuarioDto;
import com.microservicios.seguridad.client.entity.TokenRegistro;
import com.microservicios.seguridad.client.entity.Usuario;

/**
 * 
 * @author Hector
 *
 */
public interface UsuarioServicio {

	
	/**
	 * Metodo para registar el usuario
	 * @param usuarioDto
	 * @return
	 */
	Usuario registrar(UsuarioDto usuarioDto);
	
	

	/**
	 * 
	 * @param token
	 * @param usuario
	 */
	void guardarTokenRegistro(String token, Usuario usuario);


	/**
	 * Metodo para validar el toekn
	 * @param token
	 * @return
	 */
	String validarToken(String token);



	/**
	 * Generar nuevo registro de token 
	 * @param oldToken
	 * @return
	 */
	TokenRegistro generarNuevoRegistroToken(String oldToken);



	/**
	 * Buscar usuario por Email
	 * @param email
	 * @return
	 */
	Usuario buscarUsuarioPorEmail(String email);


	/**
	 * Metodo para reiniciar el password del usuario
	 * @param usuario
	 * @param token
	 */
	void reiniciarPasswordToken(Usuario usuario, String token);


	/**
	 * Validar el reinicio de password por medio del token
	 * @param token
	 * @return
	 */
	String validarReinicioPasswordToken(String token);



	/**
	 * Obtener Usuario por reinicio de pasword
	 * @param token
	 * @return
	 */
	Optional<Usuario> obtenerUsuarioPorReinicioPassword(String token);



	/**
	 * Metodo para cambiar el password
	 * @param usuario
	 * @param nuevoPassword
	 */
	void cambiarPassword(Usuario usuario, String nuevoPassword);



	/**
	 * Validar Actual password
	 * @param usuario
	 * @param actualPassword
	 * @return
	 */
	boolean validarActualPassword(Usuario usuario, String actualPassword);

}
