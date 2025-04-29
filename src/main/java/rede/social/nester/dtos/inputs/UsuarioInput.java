package rede.social.nester.dtos.inputs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioInput {

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull(message = "Data de Nascimento não pode ser nulo")
    private Date dataNascimento;

    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @NotNull(message = "Senha não pode ser nulo")
    private String senha;
}
