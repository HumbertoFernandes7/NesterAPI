package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInput {

	@NotNull(message = "Email não pode ser nulo")
	private String email;

	@NotNull(message = "Senha não pode ser nulo")
	private String senha;
}
