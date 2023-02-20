package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService; 
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		
		//Convertemos para o "DTO" do SpringSecurity
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		
		//E usamos a AuthenticationManager para disparar o processo de autenticação
		//e com isso ela chama a AutenticacaoService que chama o repository que vai no banco fazer a consulta
		//e checa se o usuário e a senha existem, se sim, retorna o ResponseEntity, senão retorna 403
		var authentication = manager.authenticate(token);
		
		return ResponseEntity.ok(tokenService.gerarToken((Usuario) authentication.getPrincipal()));
		
	}
	
}
 