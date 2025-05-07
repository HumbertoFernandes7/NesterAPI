package rede.social.nester.controllers;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import rede.social.nester.controllers.utils.MyMvcMock;
import rede.social.nester.dtos.inputs.AuthInput;
import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.inputs.UsuarioInput;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostagemCadastrarControllerTest {

    @Autowired
    private MyMvcMock mvc;

    private String uriCadastrarUsuario;

    private UsuarioInput usuarioInput;

    private AuthInput authInput;

    private String token;

    private PostagemInput postagemInput;

    private String uriCadastrarPostagem;

    @BeforeEach
    void antes() throws Exception {

        this.uriCadastrarPostagem = "/postagem/cadastrar";
        this.uriCadastrarUsuario = "/usuarios/cadastrar";

        this.usuarioInput = new UsuarioInput();
        usuarioInput.setNome("Teste");
        usuarioInput.setEmail("teste@teste.com");
        usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
        usuarioInput.setSenha("123");
        mvc.created(this.uriCadastrarUsuario, this.usuarioInput);

        authInput = new AuthInput();
        authInput.setEmail("vnsrodrigues10@gmail.com");
        authInput.setSenha("123456789");



        this.token = mvc.autenticatedWithAdminToken().getToken();

        this.postagemInput = new PostagemInput();
        postagemInput.setUsuarioId(1l);
        postagemInput.setMensagem("postagem xxxxxxxxxxxxx");

    }

    @Test
    void quando_cadastrarPostagem_RetornaOk() throws Exception {
        mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

    @Test
    void quando_cadastraPostagem_MensagemNula_RetornaErro() throws Exception {
        this.postagemInput.setMensagem(null);
        mvc.createdWithBadRequest(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

    @Test
    void quando_cadastraPostagem_MensagemEmBranco_RetornaErro() throws Exception {
        this.postagemInput.setMensagem("");
        mvc.createdWithBadRequest(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

    @Test
    void quando_cadastraPostagem_UsuarioNulo_RetornaErro() throws Exception {
        this.postagemInput.setUsuarioId(null);
        mvc.createdWithBadRequest(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

    @Test
    void quando_cadastraPostagem_UsuarioInvalido_RetornaErro() throws Exception {
        this.postagemInput.setUsuarioId(4l);
        mvc.createdWithNotFound(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

}
