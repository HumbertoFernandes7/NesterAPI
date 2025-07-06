package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioInput {

	@NotBlank(message = "Comentario não pode ser em branco")
	private String comentario;
}
