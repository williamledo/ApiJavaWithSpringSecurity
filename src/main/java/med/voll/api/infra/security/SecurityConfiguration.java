package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean //Expõe o retorno do método para o spring, devolve um objeto para o spring ou um objeto que eu possa injetar em algum controller, service, etc
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable() //desabilitando a proteção contra cross site request forgery, o token já protege 
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
				.and().build(); //desabilitando o processo de autenticação que o spring dá um formulário e a aplicação é stateful, agora é stateless
		
	}
	
}
