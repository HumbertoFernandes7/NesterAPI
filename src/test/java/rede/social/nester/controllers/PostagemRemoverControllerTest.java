package rede.social.nester.controllers;

import java.time.LocalDate;

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
import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.inputs.UsuarioInput;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostagemRemoverControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private PostagemInput postagemInput;
	private UsuarioInput usuarioInput;
	private AuthInput authInput;
	private String token;
	private String uriCadastrarUsuario;
	private String uriRemoverPostagem;
	private String uriCadastrarPostagem;

	@BeforeEach
	void antes() throws Exception {

		this.uriCadastrarPostagem = "/postagem/cadastrar";
		this.uriCadastrarUsuario = "/usuarios/cadastrar";
		this.uriRemoverPostagem = "/postagem/remover/";

		this.usuarioInput = new UsuarioInput();
		usuarioInput.setNome("Teste");
		usuarioInput.setEmail("teste@teste.com");
		usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
		usuarioInput.setSenha("123");
		mvc.created(this.uriCadastrarUsuario, this.usuarioInput);

		authInput = new AuthInput();
		authInput.setEmail("teste@teste.com");
		authInput.setSenha("123");

		this.token = mvc.autenticatedWithAdminToken().getToken();

		this.postagemInput = new PostagemInput();
		postagemInput.setMensagem("postagem xx");
		mvc.created(this.token, this.uriCadastrarPostagem, this.postagemInput);

	}

	@Test
	void quando_removerPostagem_RetornaOk() throws Exception {
		mvc.delet(this.token, this.uriRemoverPostagem + "1");
	}

	@Test
	void quando_removerPostagem_RetornaUnauthorized() throws Exception {
		this.token = mvc.autenticatedWithUserAndReturnToken(authInput).getToken();
		mvc.deletWithUnathorized(this.token, this.uriRemoverPostagem + "1");
	}
}
