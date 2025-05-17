package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResetInput {

	
	@Email(message = "Email invalido!")
	@NotBlank(message = "Email não pode ser nulo")
	private String email;
}
