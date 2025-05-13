package rede.social.nester.dtos.outputs;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostagemOutput {
	
	private Long id;
	private	String mensagem;
	private LocalDateTime dataPostagem;
}
