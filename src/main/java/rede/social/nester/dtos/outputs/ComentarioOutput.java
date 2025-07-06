package rede.social.nester.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioOutput {

	private Long id;
	
	private String comentario;
	
	private UsuarioOutput usuario;
	
}
