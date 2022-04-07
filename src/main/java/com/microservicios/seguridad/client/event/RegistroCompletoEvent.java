package com.microservicios.seguridad.client.event;

import org.springframework.context.ApplicationEvent;

import com.microservicios.seguridad.client.entity.Usuario;


/**
 * Clase para eventos en la aplicacion
 * @author Hector
 *
 */
public class RegistroCompletoEvent extends ApplicationEvent {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4418891418744225898L;

	
	private Usuario usuario;
	
	private String urlAplicacion;
	
	
	
	/**
	 * Constructor
	 * @param usuario
	 * @param urlAplicacion
	 */
	public RegistroCompletoEvent(Usuario usuario,String urlAplicacion) {
		super(usuario);
		this.usuario= usuario;
		this.urlAplicacion=urlAplicacion;
	}



	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}



	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	/**
	 * @return the urlAplicacion
	 */
	public String getUrlAplicacion() {
		return urlAplicacion;
	}



	/**
	 * @param urlAplicacion the urlAplicacion to set
	 */
	public void setUrlAplicacion(String urlAplicacion) {
		this.urlAplicacion = urlAplicacion;
	}
	
	

}
