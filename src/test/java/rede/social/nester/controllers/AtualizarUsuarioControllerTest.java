package rede.social.nester.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import rede.social.nester.controllers.utils.MyMvcMock;
import rede.social.nester.dtos.inputs.AuthInput;
import rede.social.nester.dtos.inputs.UsuarioInput;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AtualizarUsuarioControllerTest {

    @Autowired
    private MyMvcMock mvc;

    private String uricadastrarUsuario;

    private UsuarioInput usuarioInput;

    private UsuarioInput usuarioInput2;

    private String uriAtualizarUsuario;

    private String token;

    private AuthInput authInput;


    @BeforeEach
    void antes() throws Exception {

        this.uricadastrarUsuario = "/usuarios/cadastrar";
        this.uriAtualizarUsuario = "/usuarios/atualizar";

        this.usuarioInput = new UsuarioInput();
        usuarioInput.setNome("Teste");
        usuarioInput.setEmail("teste@teste.com");
        usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
        usuarioInput.setSenha("123");

        mvc.created(uricadastrarUsuario, usuarioInput);

        this.authInput = new AuthInput();
        this.authInput.setEmail("teste@teste.com");
        this.authInput.setSenha("123");

        this.usuarioInput2 = new UsuarioInput();
        this.usuarioInput2.setNome("Teste2");
        this.usuarioInput2.setEmail("teste2@teste.com");
        this.usuarioInput2.setDataNascimento(LocalDate.of(2003, 10, 11));
        this.usuarioInput2.setSenha("1234");
        mvc.created(uricadastrarUsuario, usuarioInput2);

        this.token = mvc.autenticatedWithUserAndReturnToken(authInput).getToken();
    }

    @Test
    void quando_atualizarUsuario_RetornaOk() throws Exception {
        usuarioInput.setEmail("teste3@teste.com");
        usuarioInput.setNome("nome2");
        usuarioInput.setDataNascimento(LocalDate.of(2005, 01, 10));
        usuarioInput.setSenha("12333331");
        mvc.update(this.token, uriAtualizarUsuario, usuarioInput);

    }

    @Test
    void quando_atualizarUsuario_nomeNulo_RetornaErro() throws Exception {
        usuarioInput.setNome(null);
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }

    @Test
    void quando_atualizarUsuario_nomeEmBranco_RetornaErro() throws Exception {
        usuarioInput.setNome("");
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }

    @Test
    void quando_atualizarUsuario_emailNulo_RetornaErro() throws Exception {
        usuarioInput.setEmail(null);
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }

    @Test
    void quando_atualizarUsuario_emailEmBranco_RetornaErro() throws Exception {
        usuarioInput.setEmail("");
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }

    @Test
    void quando_atualizarUsuario_dataNascimentoNulo_RetornaErro() throws Exception {
        usuarioInput.setDataNascimento(null);
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }

    @Test
    void quando_atualizarUsuario_senhaNulo_RetornaErro() throws Exception {
        usuarioInput.setSenha(null);
    }

    @Test
    void quando_atualizarUsuario_senhaEmBranco_RetornaErro() throws Exception {
        usuarioInput.setSenha("");
        mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
    }


  /*  @Test
    void quando_atualizarUsuario_emailDeOutroUsuario_RetornaErro() throws Exception {
        usuarioInput.setEmail("teste2@teste.com");
        ResultActions result = mvc.updateWithBadRequest(token, uriAtualizarUsuario, usuarioInput);
        result.andExpect(jsonPath("[?($.message == 'Email já cadastrado no sistema!')]").exists());
    }*/

}
