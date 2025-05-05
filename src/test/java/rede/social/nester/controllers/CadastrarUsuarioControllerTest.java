package rede.social.nester.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import rede.social.nester.controllers.utils.MyMvcMock;

@SpringBootTest
@AutoConfigureMockMvc

public class CadastrarUsuarioControllerTest {

	@Autowired
	private MyMvcMock mvc;

	@BeforeEach
	void antes() throws Exception {

	}
	
	@Test
	void teste() {
		
	}
}
