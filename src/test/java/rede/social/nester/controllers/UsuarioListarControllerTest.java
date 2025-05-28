package rede.social.nester.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

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
import rede.social.nester.dtos.inputs.UsuarioInput;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsuarioListarControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private UsuarioInput usuarioInput;
	private String uri;
	private String uriCadastrar;
	private String uriBuscar;
	private String token;
	private AuthInput authInput;

	@BeforeEach
	void antes() throws Exception {

		this.uri = "/usuarios/listar-todos";
		this.uriCadastrar = "/usuarios/cadastrar";
		this.uriBuscar = "/usuarios/buscar";

		this.authInput = new AuthInput();
		this.authInput.setEmail("email@hotmail.com");
		this.authInput.setSenha("123456789");

		this.usuarioInput = new UsuarioInput();
		this.usuarioInput.setNome("nome teste");
		this.usuarioInput.setEmail("email@hotmail.com");
		this.usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
		this.usuarioInput.setSenha("123456789");
		mvc.created(uriCadastrar, this.usuarioInput);

		this.usuarioInput.setNome("nome teste 2");
		this.usuarioInput.setEmail("email2@hotmail.com");
		this.usuarioInput.setDataNascimento(LocalDate.of(2002, 11, 11));
		this.usuarioInput.setSenha("123456789");
		mvc.created(uriCadastrar, this.usuarioInput);

		this.token = mvc.autenticatedWithUserAndReturnToken(authInput).getToken();
	}

	@Test
	void quando_listarTodosUsuarios_RetornaOk() throws Exception {
		ResultActions result = mvc.find(token, uri);
		result.andExpect(jsonPath("$[2].nome").value("nome teste"))
				.andExpect(jsonPath("$[2].email").value("email@hotmail.com"))
				.andExpect(jsonPath("$[2].dataNascimento").value("2002-10-10"))
				.andExpect(jsonPath("$[2].role").value("USER"))

				.andExpect(jsonPath("$[3].nome").value("nome teste 2"))
				.andExpect(jsonPath("$[3].email").value("email2@hotmail.com"))
				.andExpect(jsonPath("$[3].dataNascimento").value("2002-11-11"))
				.andExpect(jsonPath("$[3].role").value("USER"));
	}

	@Test
	void quando_listarTodosUsuarios_semToken_RetornaErro() throws Exception {
		mvc.findWithForbidden(uri);
	}

	@Test
	void quando_listarUsuarioPeloId_RetornaOk() throws Exception {
		this.token = mvc.autenticatedWithAdminToken().getToken();
		ResultActions result = mvc.find(this.token, uriBuscar + "/3");
		result.andExpect(jsonPath("$.id").value(3)).andExpect(jsonPath("$.nome").value("nome teste"))
				.andExpect(jsonPath("$.email").value("email@hotmail.com"))
				.andExpect(jsonPath("$.dataNascimento").value("2002-10-10"))
				.andExpect(jsonPath("$.role").value("USER"));
	}

	@Test
	void quando_listarUsuarioPeloId_SemToken_RetornaErro() throws Exception {
		mvc.findWithForbidden(uriBuscar + "/3");
	}

	@Test
	void quando_listarUsuarioPorString_RetornaOk() throws Exception {
		ResultActions result = mvc.find(this.token, this.uriBuscar + "?por=humb");
		result.andExpect(jsonPath("$[0].nome").value("Humberto Fernandes"));
		result.andExpect(jsonPath("$[0].email").value("humbertofernandes08@hotmail.com"));
		result.andExpect(jsonPath("$[0].dataNascimento").value("2002-08-27"));
		result.andExpect(jsonPath("$[0].role").value("ADMIN"));
	}

	@Test
	void quando_listarUsuarioPorString_SemToken_RetornaErro() throws Exception {
		mvc.findWithForbidden(this.uriBuscar + "?por=humb");
	}
}
