package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override      // 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var tokenJWT = recuperarToken(request);
		
		if (tokenJWT != null) {  //Se não estiver vindo o token, segue o fluxo, o spring vai verificar pela classe SecurityConfiguration se o usuário está logado e vai liberá-lo ou não
			var subject = tokenService.getSubject(tokenJWT); //Se estiver, recupere esse token do cabeçalho, valida se está correto e pega o email do usuário que está dentro do token
			var usuario = repository.findByLogin(subject); // Busca esse usuário do banco de dados
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Criando o DTO do SpringSecurity que representa o usuário
			
			SecurityContextHolder.getContext().setAuthentication(authentication); // Força a autenticação do usuário
			
		}	
		
		filterChain.doFilter(request, response); // Segue o fluxo da requisição
		
	}

	private String recuperarToken(HttpServletRequest request) {
		
		var authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		
		return null;
		
	}

	
	
}
