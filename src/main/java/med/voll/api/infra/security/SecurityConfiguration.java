package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean //Serve para exportar uma classe para o Spring, fazendo com que el consiga carregá-la e realize a injeção de dependência em outras classes
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable() //desabilitando a proteção contra cross site request forgery, o token já protege 
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
				.and().authorizeRequests() //Configura como vai ser a autorização das requisições
				.antMatchers(HttpMethod.POST, "/login").permitAll() //Se vier um post para "/login", libere, não precisa chegar se o usuário está com token
				.anyRequest().authenticated() // Qualquer outra requisição, o usuário precisa estar autenticado
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Configurando o meu filtro para vir antes do spring, assim ele irá falar que meu usuário está logado
				.build(); //desabilitando o processo de autenticação que o spring dá um formulário e a aplicação é stateful, agora é stateless
	
	}
	
	@Bean // Estou mostrando para o spring como ele injeta objetos
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
