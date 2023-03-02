package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

public record DadosAgendamentoConsulta (
		
		Long idMedico,
		
		@NotNull
		Long idPaciente,
		
		@NotNull
		@Future //Só aceita datas no futuro
		LocalDateTime data,
		
		Especialidade especialidade
		) {
	
}