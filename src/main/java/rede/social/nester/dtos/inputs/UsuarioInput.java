package rede.social.nester.dtos.inputs;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@NotBlank(message = "Nome não pode ser nulo")
	private String nome;

	@NotNull(message = "Data nascimento não pode ser nulo")
	private LocalDate dataNascimento;

	@NotBlank(message = "Email não pode ser nulo")
	private String email;

	@NotBlank(message = "Senha não pode ser nulo")
	private String senha;
}
