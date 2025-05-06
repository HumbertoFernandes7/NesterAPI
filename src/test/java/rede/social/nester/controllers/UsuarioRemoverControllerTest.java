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
import rede.social.nester.dtos.inputs.UsuarioInput;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsuarioRemoverControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private UsuarioInput usuarioInput;
	private AuthInput authInput;
	private String uriRemover;
	private String uriCadastrar;
	private String token;

	@BeforeEach
	void antes() throws Exception {
		this.uriRemover = "/usuarios/remover/";
		this.uriCadastrar = "/usuarios/cadastrar";

		this.usuarioInput = new UsuarioInput();
		this.usuarioInput.setNome("Teste");
		this.usuarioInput.setEmail("teste@teste.com");
		this.usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
		this.usuarioInput.setSenha("123");
		mvc.created(this.uriCadastrar, this.usuarioInput);

		this.token = mvc.autenticatedWithAdminToken().getToken();
		
		this.authInput = new AuthInput();
		this.authInput.setEmail("teste@teste.com");
		this.authInput.setSenha("123");
	}

	@Test
	void quando_removerUsuario_RetornaOk() throws Exception {
		mvc.delet(this.token, uriRemover + "2");
	}
	
	@Test
	void quando_removerUsuario_RetornaUnauthorized() throws Exception {
		this.token = mvc.autenticatedWithUserAndReturnToken(authInput).getToken();
		mvc.deletWithUnathorized(this.token, uriRemover + "2");
	}
}
