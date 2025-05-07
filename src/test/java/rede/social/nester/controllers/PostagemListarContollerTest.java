package rede.social.nester.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import rede.social.nester.controllers.utils.MyMvcMock;
import rede.social.nester.dtos.inputs.AuthInput;
import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.inputs.UsuarioInput;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostagemListarContollerTest {

    @Autowired
    private MyMvcMock mvc;

    private String uriCadastrarUsuario;

    private UsuarioInput usuarioInput;

    private AuthInput authInput;

    private String token;

    private PostagemInput postagemInput;

    private String uriCadastrarPostagem;

    private String uriListarPostagem;

    @BeforeEach
    void antes() throws Exception {

        this.uriCadastrarPostagem = "/postagem/cadastrar";
        this.uriCadastrarUsuario = "/usuarios/cadastrar";
        this.uriListarPostagem = "/postagem/usuario";

        this.usuarioInput = new UsuarioInput();
        usuarioInput.setNome("Teste");
        usuarioInput.setEmail("teste@teste.com");
        usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
        usuarioInput.setSenha("123");
        mvc.created(this.uriCadastrarUsuario, this.usuarioInput);


        this.token = mvc.autenticatedWithAdminToken().getToken();

        this.postagemInput = new PostagemInput();
        postagemInput.setMensagem("postagem xx");
        mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);

        postagemInput.setMensagem("postagem xxx");
        mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);

    }

    @Test
    void quando_ListarPostagem_UsuarioLogado_RetornaOk() throws Exception {
       ResultActions result = mvc.find(token, uriListarPostagem);
        result.andExpect(jsonPath("$[0].mensagem").value("postagem xx"))
                .andExpect(jsonPath("$[1].mensagem").value("postagem xxx"));
    }

    @Test
    void quando_ListarPostagemDoUsuarioPeloId_RetornaOk() throws Exception {
        ResultActions result = mvc.find(token, uriListarPostagem + "/2");
        result.andExpect(jsonPath("$[0].mensagem").value("postagem xx"));
    }

    @Test
    void quando_listarPostagem_SemToken_RetornaErro() throws Exception {
        mvc.findWithForbidden(uriListarPostagem);
    }

    @Test
    void quando_listarPostagemDoUsuarioPeloId_SemToken_RetornErro() throws Exception {
        mvc.findWithForbidden(uriListarPostagem + "/2");
    }


}