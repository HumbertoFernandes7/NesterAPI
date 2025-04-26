package rede.social.nester.dtos.inputs;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioInput {

    private String nome;

    private Date dataNascimento;

    private String email;

    private String senha;
}
