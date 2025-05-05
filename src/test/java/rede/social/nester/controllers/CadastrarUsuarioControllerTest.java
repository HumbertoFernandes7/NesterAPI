package rede.social.nester.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import rede.social.nester.controllers.utils.MyMvcMock;
import rede.social.nester.dtos.inputs.UsuarioInput;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastrarUsuarioControllerTest {

	@Autowired
	private MyMvcMock mvc;

	private UsuarioInput usuarioInput;

	private String uri;

	@BeforeEach
	void antes() throws Exception {

		this.uri = "/usuarios/cadastrar";

		this.usuarioInput = new UsuarioInput();
		usuarioInput.setNome("Teste");
		usuarioInput.setEmail("teste@teste.com");
		usuarioInput.setDataNascimento(LocalDate.of(2002, 10, 10));
		usuarioInput.setSenha("123");
	}

	@Test
	void quando_cadastrarUsuario_RetornaOk() throws Exception {
		usuarioInput.setEmail("teste3@teste.com");
		mvc.created(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_NomeNulo_RetornaErro() throws Exception {
		usuarioInput.setNome(null);
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_NomeEmBranco_RetornaErro() throws Exception {
		usuarioInput.setNome("");
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_EmailNulo_RetornaErro() throws Exception {
		usuarioInput.setEmail(null);
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_EmailEmBranco_RetornaErro() throws Exception {
		usuarioInput.setEmail("");
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_DataNascimentoNulo_RetornaErro() throws Exception {
		usuarioInput.setDataNascimento(null);
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_SenhaNulo_RetornaErro() throws Exception {
		usuarioInput.setSenha(null);
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuario_SenhaEmBranco_RetornaErro() throws Exception {
		usuarioInput.setSenha("");
		mvc.createdWithBadRequest(uri, usuarioInput);
	}

	@Test
	void quando_cadastrarUsuarioEmailExistente_RetornaErro() throws Exception {
		mvc.created(uri, usuarioInput);
		UsuarioInput usuarioInput2 = new UsuarioInput();
		usuarioInput2.setNome("nome2");
		usuarioInput2.setEmail("teste@teste.com");
		usuarioInput2.setDataNascimento(LocalDate.of(2002, 10, 10));
		usuarioInput2.setSenha("123");
		ResultActions result = mvc.createdWithBadRequest(uri, usuarioInput);
		result.andExpect(jsonPath("[?($.message == 'Email já cadastrado no sistema!')]").exists());
	}
}
