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


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CurtidaCadastrarControllerTest {
    @Autowired
    private MyMvcMock mvc;

    private AuthInput authInput;

    private String token;

    private PostagemInput postagemInput;

    private String uriCadastrarPostagem;

    private String uriCurtir;

    @BeforeEach
    void antes() throws Exception {
        this.uriCadastrarPostagem = "/postagem/cadastrar";
        this.uriCurtir = "/curtida/";

        this.authInput = new AuthInput();
        this.authInput.setEmail("humbertofernandes08@hotmail.com");
        this.authInput.setSenha("123456789");

        this.token = mvc.autenticatedWithAdminToken().getToken();

        this.postagemInput = new PostagemInput();
        postagemInput.setMensagem("postagem xx");
        mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);
    }

    @Test
    void quando_CurtirRetornaOk() throws Exception {
        mvc.created(this.token, this.uriCurtir + "1");
    }

    @Test
    void quando_CurtirPostagemNaoExistente_RetornaErro() throws Exception {
        mvc.createdWithNotFound(this.token, this.uriCurtir + "2", null);
    }

    @Test
    void quando_CurtirPostagem_semTokem_RetornaErro() throws Exception {
        mvc.createdWithForbidden(this.uriCurtir + "1", null);
    }

    @Test
    void quando_curtirPostagem_2vezes_RetornaErro() throws Exception {
        mvc.created(this.token, this.uriCurtir + "1");
        ResultActions result = mvc.createdWithBadRequest(this.token, this.uriCurtir + "1", null);
        result.andExpect(jsonPath("[?($.message == 'Usuario ja curtiu a publicação')]").exists());
    }

    @Test
    void quando_DescurtirPostagem_semTokem_RetornaErro() throws Exception {
        mvc.deletWithNoToken(this.uriCurtir + "1");
    }

    @Test
    void quando_DescurtirRetornaOk() throws Exception {
        mvc.created(this.token, this.uriCurtir + "1");
        mvc.delet(this.token, this.uriCurtir + "1");
    }

    @Test
    void quando_descurtirPostagem_PorOutroUsuario_RetornaErro() throws Exception{
        mvc.created(this.token, this.uriCurtir + "1");
        String tokenUsuario = mvc.autenticatedWithUserAndReturnToken(this.authInput).getToken();
        ResultActions result = mvc.deletWithNotFound(tokenUsuario, this.uriCurtir + "1");
        result.andExpect(jsonPath("[?($.message == 'Curtida não encontrada pelo usuario na postagem com id: 1')]").exists());
    }

}
