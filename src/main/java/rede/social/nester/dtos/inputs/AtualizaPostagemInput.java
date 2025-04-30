package rede.social.nester.dtos.inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizaPostagemInput {

    @NotNull(message = "o campo mensagem não pode ser nulo!")
    private String mensagem;
}
