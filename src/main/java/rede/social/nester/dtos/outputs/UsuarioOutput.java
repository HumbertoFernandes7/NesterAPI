package rede.social.nester.dtos.outputs;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String email;
}
