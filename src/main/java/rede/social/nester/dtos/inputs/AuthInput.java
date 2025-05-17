package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInput {

	@NotBlank(message = "Email não pode ser nulo")
	private String email;

	@Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
	@NotBlank(message = "Senha não pode ser nulo")
	private String senha;
}
