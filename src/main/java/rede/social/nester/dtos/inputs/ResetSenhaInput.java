package rede.social.nester.dtos.inputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaInput {

	private String senha;
	private String repetirSenha;
}
