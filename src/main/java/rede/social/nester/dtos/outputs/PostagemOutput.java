package rede.social.nester.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemOutput {
	private Long id;
	private	String mensagem;
	private UsuarioOutput usuario;
}
