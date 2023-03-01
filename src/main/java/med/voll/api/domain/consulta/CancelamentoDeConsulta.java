package med.voll.api.domain.consulta;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelamentoDeConsulta {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void cancelarConsulta(DadosCancelamentoDeConsulta dados) {
		
		var agora = LocalDateTime.now();
		var data = dados.consulta().getData();
		
		var duracao = Duration.between(agora, data);
		
		if (duracao.toHours() < 24) {
			throw new RuntimeException("Impossível cancelar, a consulta está a menos de 24 horas da hora atual!");
		}
		
		consultaRepository.delete(dados.consulta());
		
		
	}
	
}
