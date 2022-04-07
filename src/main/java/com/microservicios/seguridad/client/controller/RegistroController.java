package com.microservicios.seguridad.client.controller;


import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.seguridad.client.dto.PasswordDto;
import com.microservicios.seguridad.client.dto.UsuarioDto;
import com.microservicios.seguridad.client.entity.TokenRegistro;
import com.microservicios.seguridad.client.entity.Usuario;
import com.microservicios.seguridad.client.event.RegistroCompletoEvent;
import com.microservicios.seguridad.client.service.UsuarioServicio;

/**
 * Controlador de registro de usuario
 * @author Hector
 *
 */
@RestController
public class RegistroController {
	
	
	/**
	 * Servicio de usaurio
	 */
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	/**
	 * 
	 */
	private static Logger log= LoggerFactory.getLogger(RegistroController.class);
	
	/**
	 * Eventos publisher de aplicacion
	 */
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("/registrar")
	public String registrar(@RequestBody UsuarioDto usuarioDto,final HttpServletRequest request) {
		
		Usuario usuario = usuarioServicio.registrar(usuarioDto);
		publisher.publishEvent(new RegistroCompletoEvent(usuario,aplicacionUrl(request)));
		return "Success";
	}
	
	/**
	 * metodo de prueba
	 * @return
	 */
	@GetMapping("/hola")
	public String registrar() {
		return "Hola Servicio funcionando";
	}
	
	
	/**
	 * Metodo para verificar el registro del usuario
	 * @param token
	 * @return
	 */
	@GetMapping("/verificarRegistro")
	public String verificarToken(@RequestParam(name="token") String token) {
		String resultado = usuarioServicio.validarToken(token);
		
		if(resultado.equalsIgnoreCase("OK")) {
			resultado="Usuario Verificado Correctamente";
		}
		
		return resultado;
	}
	
	/**
	 * Metodo para reenviar el token 
	 * @param oldToken
	 * @param request
	 * @return
	 */
	@GetMapping("/reenviarToken")
	public String reenviarRegistroToken(@RequestParam(name="token")  String oldToken, HttpServletRequest request) {
		
		TokenRegistro tokenRegistro= usuarioServicio.generarNuevoRegistroToken(oldToken); 
		Usuario usuario= tokenRegistro.getUsuario();
		reenviarTokenRegistroMail(usuario,aplicacionUrl(request),tokenRegistro.getToken());
		return "Link de reviricacion Token";
	}
	
	
	/**
	 * Metodo para iniciar el reinicio de password
	 * @param passwordDto
	 * @param request
	 * @return
	 */
	@GetMapping("/reinicarPassword")
	public String reiniciarPassword(@RequestBody PasswordDto passwordDto,HttpServletRequest request) {
		
		Usuario usuario= usuarioServicio.buscarUsuarioPorEmail(passwordDto.getEmail());
		String url="";
		if(usuario!=null) {
			String token=UUID.randomUUID().toString();
			usuarioServicio.reiniciarPasswordToken(usuario,token);
			url=reiniciarPasswordMail(usuario,aplicacionUrl(request),token);
		}
		return url;
	}
	
	
	@PostMapping("/guardarPassword")
	public String guardarPassword(@RequestParam("token") String token,@RequestBody PasswordDto passwordDto) {
		String resultado = usuarioServicio.validarReinicioPasswordToken(token);
		
		if(!resultado.equalsIgnoreCase("OK")) {
			return resultado;
		}
		
		Optional<Usuario> usuario = usuarioServicio.obtenerUsuarioPorReinicioPassword(token);
		
		if(usuario.isPresent()){
			
			usuarioServicio.cambiarPassword(usuario.get(),passwordDto.getNuevoPassword());
			
			 return resultado="Reinicio de password correctamente";
			
		}else {
			return resultado="Token Invalido";
		}
		
	}
	
	
	/**
	 * Metodo para cambiar el password
	 * @param passwordDto
	 * @return
	 */
	@PutMapping("/cambiarPassword")
	public String cambiarPaswor(@RequestBody PasswordDto passwordDto){
		
		Usuario usuario= usuarioServicio.buscarUsuarioPorEmail(passwordDto.getEmail());
		
		if(usuario==null) {
			return "Usuario no existe";
		}
		else if(!usuarioServicio.validarActualPassword(usuario,passwordDto.getActualPassword())){
			return "Password actual invalido";
		}
		
		usuarioServicio.cambiarPassword(usuario,passwordDto.getNuevoPassword());
		return "Password Cambiado Correctamente";
	}

	/**
	 * Metodo para enviar el email cuando se reinicia el password
	 * @param usuario
	 * @param aplicacionUrl
	 * @return
	 */
	private String reiniciarPasswordMail(Usuario usuario, String aplicacionUrl,String token) {
		String url= aplicacionUrl+"/guardarPassword?token="+token ;
		//Mandar Email
		log.info("reiniciarPasswordMail token: {}",url);
		return url;
	}

	/**
	 * Metodo para enviar correo para reenviar el token
	 * @param usuario
	 * @param aplicacionUrl
	 * @param token
	 */
	private void reenviarTokenRegistroMail(Usuario usuario, String aplicacionUrl,String token) {
		String url= aplicacionUrl+"/verificarRegistro?token="+token ;
		//Mandar Email
		log.info("reenviarTokenRegistroMail token: {}",url);
		
	}


	/**
	 * crear la url de la aplicacion
	 * @param request
	 * @return
	 */
	private String aplicacionUrl(HttpServletRequest request) {
		
		return "http://"+request.getServerName()
				+":"+request.getServerPort();
				//+"/"+request.getContextPath();
	}

}
