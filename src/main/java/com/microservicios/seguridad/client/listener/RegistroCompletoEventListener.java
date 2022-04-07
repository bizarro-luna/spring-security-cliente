package com.microservicios.seguridad.client.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.microservicios.seguridad.client.entity.Usuario;
import com.microservicios.seguridad.client.event.RegistroCompletoEvent;
import com.microservicios.seguridad.client.service.UsuarioServicio;

/**
 * 
 * @author Hector
 *
 */
@Component
public class RegistroCompletoEventListener  implements ApplicationListener<RegistroCompletoEvent>  {

	/**
	 * log
	 */
	private static Logger log= LoggerFactory.getLogger(RegistroCompletoEventListener.class);
	

	/**
	 * Servicio de usuario
	 */
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	
	/**
	 * Se implementa el metodo de la interfaz {@link ApplicationListener}
	 */
	@Override
	public void onApplicationEvent(RegistroCompletoEvent event) {
		
		//Verificar el token para un usuario que ya se encuentra registrado 
					   //este usuario es el que viene desde el evento
		Usuario usuario = event.getUsuario();
		String token = UUID.randomUUID().toString();
		
		usuarioServicio.guardarTokenRegistro(token,usuario);
		
		String url= event.getUrlAplicacion()+"/verificarRegistro?token="+token ;
		log.info("Veririfar token: {}",url);
		
		//enviar el email
	}

	
	
	
}
