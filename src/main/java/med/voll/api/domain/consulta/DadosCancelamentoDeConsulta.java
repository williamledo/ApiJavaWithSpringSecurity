package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoDeConsulta(
		
		@NotNull
		Consulta consulta,
		
		@NotNull
		MotivoDoCancelamento motivoDoCancelamento
		
		) {

}
