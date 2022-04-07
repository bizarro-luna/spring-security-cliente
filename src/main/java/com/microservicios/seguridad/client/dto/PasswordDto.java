package com.microservicios.seguridad.client.dto;

import java.io.Serializable;

/**
 * DTO para el reset del password
 * @author Hector
 *
 */
public class PasswordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8858494656936423331L;
	
	/**
	 * Correo del usuario
	 */
	private String email;
	
	
	private String actualPassword;
	
	private String nuevoPassword;


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	


	/**
	 * @return the actualPassword
	 */
	public String getActualPassword() {
		return actualPassword;
	}


	/**
	 * @param actualPassword the actualPassword to set
	 */
	public void setActualPassword(String actualPassword) {
		this.actualPassword = actualPassword;
	}


	/**
	 * @return the nuevoPassword
	 */
	public String getNuevoPassword() {
		return nuevoPassword;
	}


	/**
	 * @param nuevoPassword the nuevoPassword to set
	 */
	public void setNuevoPassword(String nuevoPassword) {
		this.nuevoPassword = nuevoPassword;
	}
	
	
	
	

}
