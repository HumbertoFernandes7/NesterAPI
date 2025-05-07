package rede.social.nester.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import rede.social.nester.controllers.utils.MyMvcMock;
import rede.social.nester.dtos.inputs.AtualizaPostagemInput;
import rede.social.nester.dtos.inputs.PostagemInput;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostagemAtualizarControllerTest {

    @Autowired
    private MyMvcMock mvc;

    private String token;

    private PostagemInput postagemInput;

    private AtualizaPostagemInput atualizaPostagemInput;

    private String uriCadastrarPostagem;

    private String uriAtualizarPostagem;

    @BeforeEach
    void antes() throws Exception {

        this.uriCadastrarPostagem = "/postagem/cadastrar";
        this.uriAtualizarPostagem = "/postagem/atualizar/";

        this.token = mvc.autenticatedWithAdminToken().getToken();

        this.postagemInput = new PostagemInput();
        postagemInput.setUsuarioId(1l);
        postagemInput.setMensagem("postagem xxxxxxxxxxxxx");
        mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);

        this.atualizaPostagemInput = new AtualizaPostagemInput();
    }

    @Test
    void quando_atualizarPostagem_RetornaOk() throws Exception {
        atualizaPostagemInput.setMensagem("postagem atualizada");
        ResultActions result = mvc.update(this.token, this.uriAtualizarPostagem + "1", atualizaPostagemInput);
        result.andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mensagem").value("postagem atualizada"));
    }

    @Test
    void quando_atualizaPostagem_MensagemNulo_RetornaErro() throws Exception{
        atualizaPostagemInput.setMensagem(null);
        mvc.updateWithBadRequest(this.token, this.uriAtualizarPostagem + "1", atualizaPostagemInput);
    }

    @Test
    void quando_atualizaPostagem_MensagemEmBranco_RetornaErro() throws Exception{
        atualizaPostagemInput.setMensagem("");
        mvc.updateWithBadRequest(this.token, this.uriAtualizarPostagem + "1", atualizaPostagemInput);
    }
}
