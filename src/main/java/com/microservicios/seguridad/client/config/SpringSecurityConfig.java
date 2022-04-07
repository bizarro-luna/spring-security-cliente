package com.microservicios.seguridad.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase para configurar la seguridad
 * @author Hector
 *
 */
@EnableWebSecurity
public class SpringSecurityConfig   {

	
	private static final String[] URL_PERMITIDAS= {"/","/hola","/registrar","/verificarRegistro","/reenviarToken","/reinicarPassword","/guardarPassword","/cambiarPassword"
			,"/error", "/webjars/**"};
	 
	/**
	 * Bean para encriptar cadenas
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()
			.authorizeRequests()
			.antMatchers(URL_PERMITIDAS).permitAll()
			.antMatchers("/api/**").authenticated()
			//Oauth2
			.and()
			.oauth2Login(oauth2Login-> oauth2Login.loginPage("/oauth2/autorization/api-client-oidc"))
			.oauth2Client(Customizer.withDefaults());
			
			//.anyRequest().authenticated()
			//.and().formLogin().permitAll();
		
	}
	*/
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(URL_PERMITIDAS).permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login(oauth2login ->
                        oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }

}
