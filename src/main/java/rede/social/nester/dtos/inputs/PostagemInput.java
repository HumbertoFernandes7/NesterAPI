package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemInput {

	@NotBlank(message = "o campo mensagem não pode ser nulo!")
	private String mensagem;
}
