package rede.social.nester.controllers;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    void quando_cadastraUsuarioRetornaOk() throws Exception {
        mvc.created(uri, usuarioInput);
    }
}
