package com.microservicios.seguridad.client.dto;

import java.io.Serializable;



/**
 * DTO para usuario
 * @author Hector
 *
 */
public class UsuarioDto implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2749261603197527452L;


	private String nombre;
	
	private String apellido;
	
	private String email;
	
	private String password;
	
	private String matchPassword;
	
	
	


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}


	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	


	/**
	 * @return the matchPassword
	 */
	public String getMatchPassword() {
		return matchPassword;
	}


	/**
	 * @param matchPassword the matchPassword to set
	 */
	public void setMatchPassword(String matchPassword) {
		this.matchPassword = matchPassword;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDto [nombre=");
		builder.append(nombre);
		builder.append(", apellido=");
		builder.append(apellido);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", matchPassword=");
		builder.append(matchPassword);
		builder.append("]");
		return builder.toString();
	}


	
	
	
	

}
