package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaInput {

	@NotBlank(message = "Senha não pode ser nulo")
	@Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
	private String senha;

	@NotBlank(message = "Repetir senha não pode ser nulo")
	@Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
	private String repetirSenha;
}
