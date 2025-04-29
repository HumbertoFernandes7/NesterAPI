package rede.social.nester.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioOutput {

    private Long id;

    private String nome;

    private Date dataNascimento;

    private String email;
}
