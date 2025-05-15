package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaInput {

	@NotNull(message = "Senha não pode ser nulo")
	private String senha;
	
	@NotNull(message = "Repetir senha não pode ser nulo")
	private String repetirSenha;
}
