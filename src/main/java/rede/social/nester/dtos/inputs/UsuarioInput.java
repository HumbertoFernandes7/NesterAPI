package rede.social.nester.dtos.inputs;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull(message = "Data de Nascimento não pode ser nulo")
    private LocalDate dataNascimento;

    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @NotNull(message = "Senha não pode ser nulo")
    private String senha;
}
