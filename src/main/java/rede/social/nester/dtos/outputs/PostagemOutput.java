package rede.social.nester.dtos.outputs;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemOutput {

	private Long id;
	private String mensagem;
	private LocalDateTime dataPostagem;
	private List<CurtidaOutput> curtidas;
	private UsuarioOutput usuario;
}
