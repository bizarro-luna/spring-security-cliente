package com.microservicios.seguridad.client.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.seguridad.client.dto.UsuarioDto;
import com.microservicios.seguridad.client.entity.ReinicioPasswordToken;
import com.microservicios.seguridad.client.entity.TokenRegistro;
import com.microservicios.seguridad.client.entity.Usuario;
import com.microservicios.seguridad.client.repository.ReinicioPasswordTokenRepository;
import com.microservicios.seguridad.client.repository.TokenRegistroRepository;
import com.microservicios.seguridad.client.repository.UsuarioRepository;
import com.microservicios.seguridad.client.service.UsuarioServicio;

/**
 * Negocio para el registro de usuario
 * @author Hector
 *
 */
@Service
public class UsuarioServicioImpl implements UsuarioServicio{
	
	
	private static Logger log= LoggerFactory.getLogger(UsuarioServicioImpl.class);
	
	/**
	 * Repositorio de usuario
	 */
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Repositorio de Registro de token
	 */
	@Autowired
	private TokenRegistroRepository tokenRepository;
	
	/**
	 * Repositorio parea reiniciar el password
	 */
	@Autowired
	private ReinicioPasswordTokenRepository reinicioRepository;
	
	/**
	 * BCryp
	 */
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	
	/**
	 * Tiempo de ecxpiracion del token en minutos
	 */
	private static final Long TIEMPO_EXPIRACION_TOKEN =10L;
	

	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#registrar(com.microservicios.seguridad.client.dto.UsuarioDto)
	 */
	@Override
	@Transactional
	public Usuario registrar(UsuarioDto usuarioDto) {
		
		Usuario usuario= new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		usuario.setRole("USUARIO");
		
		
		return usuarioRepository.save(usuario);
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#guardarTokenRegistro(java.lang.String, com.microservicios.seguridad.client.entity.Usuario)
	 */
	@Override
	@Transactional
	public void guardarTokenRegistro(String token, Usuario usuario) {
		
		TokenRegistro tokenRegistro= new TokenRegistro(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(TIEMPO_EXPIRACION_TOKEN), usuario);
		log.info(tokenRegistro.toString());
		tokenRepository.save(tokenRegistro);
		
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#validarToken(java.lang.String)
	 */
	@Override
	@Transactional
	public String validarToken(String token) {
		
		TokenRegistro tokenRegistro=tokenRepository.findByToken(token);
		
		if(tokenRegistro==null) {
			return"Token no Registrado";
		}
		
		
		if(LocalDateTime.now().isAfter(tokenRegistro.getExpira())) {
			tokenRepository.delete(tokenRegistro);
			return "Token expirado";
		}
		
		Usuario usuario= tokenRegistro.getUsuario();
		usuario.setEnable(true);
		usuarioRepository.save(usuario);
		
		tokenRegistro.setVerificado(LocalDateTime.now());
		tokenRepository.save(tokenRegistro);
		
		return "OK";
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#generarNuevoRegistroToken(java.lang.String)
	 */
	@Override
	@Transactional
	public TokenRegistro generarNuevoRegistroToken(String oldToken) {
		TokenRegistro tokenRegistro=tokenRepository.findByToken(oldToken);
		tokenRegistro.setCreado(LocalDateTime.now());
		tokenRegistro.setToken(UUID.randomUUID().toString());
		tokenRegistro.setExpira(LocalDateTime.now().plusMinutes(TIEMPO_EXPIRACION_TOKEN));
		tokenRepository.save(tokenRegistro);
		return tokenRegistro;
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#buscarUsuarioPorEmail(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#reiniciarPasswordToken(com.microservicios.seguridad.client.entity.Usuario, java.lang.String)
	 */
	@Override
	@Transactional
	public void reiniciarPasswordToken(Usuario usuario, String token) {
		ReinicioPasswordToken reinicio= new ReinicioPasswordToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(TIEMPO_EXPIRACION_TOKEN),usuario);
		reinicioRepository.save(reinicio);
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#validarReinicioPasswordToken(java.lang.String)
	 */
	@Override
	@Transactional
	public String validarReinicioPasswordToken(String token) {
		ReinicioPasswordToken tokenRegistro=reinicioRepository.findByToken(token);
		
		if(tokenRegistro==null) {
			return"Token no Registrado";
		}
		
		
		if(LocalDateTime.now().isAfter(tokenRegistro.getExpira())) {
			reinicioRepository.delete(tokenRegistro);
			return "Token expirado";
		}
		
		
		tokenRegistro.setVerificado(LocalDateTime.now());
		reinicioRepository.save(tokenRegistro);
		
		return "OK";
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#obtenerUsuarioPorReinicioPassword(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> obtenerUsuarioPorReinicioPassword(String token) {
		return Optional.ofNullable(reinicioRepository.findByToken(token).getUsuario());
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#cambiarPassword(com.microservicios.seguridad.client.entity.Usuario, java.lang.String)
	 */
	@Override
	@Transactional
	public void cambiarPassword(Usuario usuario, String nuevoPassword) {
		usuario.setPassword(passwordEncoder.encode(nuevoPassword));
		usuarioRepository.save(usuario);
		log.info("Cambio de password: {}",usuario.toString());
	}


	/*
	 * (non-Javadoc)
	 * @see com.microservicios.seguridad.client.service.UsuarioServicio#validarActualPassword(com.microservicios.seguridad.client.entity.Usuario, java.lang.String)
	 */
	@Override
	public boolean validarActualPassword(Usuario usuario, String actualPassword) {
		return passwordEncoder.matches(actualPassword, usuario.getPassword());
	}


	
}
