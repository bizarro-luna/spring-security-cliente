package com.microservicios.seguridad.client.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Tabla de tokens
 * @author Hector
 *
 */
@Entity
@Table(name="registros_token")
public class TokenRegistro implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2089260259304744357L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * token 
	 */
	private String token;
	
	/**
	 * fecha de creacion
	 */
	private LocalDateTime creado;
	
	/**
	 * fecha con tiempo de expiracion del token
	 */
	private LocalDateTime expira;
	
	/**
	 * fecha y tiempo en que se veririfico el token
	 */
	private LocalDateTime verificado;
	
	@OneToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	
	public TokenRegistro() {
		
	}
	
	public TokenRegistro(String token,LocalDateTime expira) {
		super();
		this.token = token;
		this.expira = expira;
	}

	/**
	 * 
	 * @param token
	 * @param creado
	 * @param expira
	 * @param usuario
	 */
	public TokenRegistro(String token, LocalDateTime creado, LocalDateTime expira, Usuario usuario) {
		super();
		this.token = token;
		this.creado = creado;
		this.expira = expira;
		this.usuario = usuario;
	}




	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the creado
	 */
	public LocalDateTime getCreado() {
		return creado;
	}

	/**
	 * @param creado the creado to set
	 */
	public void setCreado(LocalDateTime creado) {
		this.creado = creado;
	}

	/**
	 * @return the expira
	 */
	public LocalDateTime getExpira() {
		return expira;
	}

	/**
	 * @param expira the expira to set
	 */
	public void setExpira(LocalDateTime expira) {
		this.expira = expira;
	}

	/**
	 * @return the verificado
	 */
	public LocalDateTime getVerificado() {
		return verificado;
	}

	/**
	 * @param verificado the verificado to set
	 */
	public void setVerificado(LocalDateTime verificado) {
		this.verificado = verificado;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Token [id=");
		builder.append(id);
		builder.append(", token=");
		builder.append(token);
		builder.append(", creado=");
		builder.append(creado);
		builder.append(", expira=");
		builder.append(expira);
		builder.append(", verificado=");
		builder.append(verificado);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
