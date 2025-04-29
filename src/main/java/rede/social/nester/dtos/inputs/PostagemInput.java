package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemInput {

	@NotNull(message = "o campo mensagem não pode ser nulo!")
	private String mensagem;
	
	@NotNull(message = "o Id do usuario não pode ser nulo")
	private Long usuarioId;
}
