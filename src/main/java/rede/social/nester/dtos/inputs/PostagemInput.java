package rede.social.nester.dtos.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemInput {

	private String mensagem;
	
	private Long usuarioId;
}
