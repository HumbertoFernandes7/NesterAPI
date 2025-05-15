package rede.social.nester.controllers.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import rede.social.nester.dtos.inputs.AuthInput;
import rede.social.nester.dtos.outputs.TokenOutput;

@Component
public class MyMvcMock {

	@Autowired
	private MockMvc mvc;

	public ResultActions autenticated(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isOk());
	}

	public TokenOutput autenticatedWithAdminToken() throws Exception {
		AuthInput authInput = new AuthInput();
		authInput.setEmail("vnsrodrigues10@gmail.com");
		authInput.setSenha("123456789");
		MvcResult result = this.autenticated("/auth/login", authInput).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		TokenOutput tokenOutput = new ObjectMapper().readValue(contentAsString, TokenOutput.class);
		return tokenOutput;
	}

	public TokenOutput autenticatedWithUserAndReturnToken(Object objeto) throws Exception {
		MvcResult result = this.autenticated("/auth/login", objeto).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		TokenOutput tokenOutput = new ObjectMapper().readValue(contentAsString, TokenOutput.class);
		return tokenOutput;
	}

	public ResultActions created(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isCreated());
	}

	public ResultActions created(String token, String uri, Object objeto) throws Exception {
		return sendPost(token, uri, objeto).andExpect(status().isCreated());
	}

	public ResultActions created(String token, String uri) throws Exception {
		return sendPost(token, uri).andExpect(status().isCreated());
	}

	public ResultActions createdWithBadRequest(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isBadRequest());
	}

	public ResultActions createdWithForbidden(String uri, Object objeto) throws Exception {
		return sendPost(uri, objeto).andExpect(status().isForbidden());
	}

	public ResultActions createdWithBadRequest(String token, String uri, Object objeto) throws Exception {
		return sendPost(token, uri, objeto).andExpect(status().isBadRequest());
	}

	public ResultActions createdWithNotFound(String token, String uri, Object objeto) throws Exception {
		return sendPost(token, uri, objeto).andExpect(status().isNotFound());
	}

	public ResultActions update(String token, String uri, Object objeto) throws Exception {
		return sendPut(token, uri, objeto).andExpect(status().isOk());
	}

	public ResultActions updateWithBadRequest(String token, String uri, Object objeto) throws Exception {
		return sendPut(token, uri, objeto).andExpect(status().isBadRequest());
	}

	public ResultActions updateWithUnathorized(String token, String uri, Object objeto) throws Exception {
		return sendPut(token, uri, objeto).andExpect(status().isForbidden());
	}

	public ResultActions find(String token, String uri) throws Exception {
		return sendGet(token, uri).andExpect(status().isOk());
	}

	public ResultActions findWithForbidden(String uri) throws Exception {
		return sendGet(uri).andExpect(status().isForbidden());
	}

	public ResultActions delet(String token, String uri) throws Exception {
		return sendDelet(token, uri).andExpect(status().isOk());
	}

	public ResultActions deletWithUnathorized(String token, String uri) throws Exception {
		return sendDelet(token, uri).andExpect(status().isUnauthorized());
	}

	public ResultActions deletWithNotFound(String token, String uri) throws Exception {
		return sendDelet(token, uri).andExpect(status().isNotFound());
	}

	public ResultActions deletWithForbiden(String token, String uri) throws Exception {
		return sendDelet(token, uri).andExpect(status().isForbidden());
	}

	public ResultActions deletWithNoToken(String uri) throws Exception {
		return sendDelet(uri).andExpect(status().isForbidden());
	}

	// Performs
	private ResultActions sendPost(String uri, Object objeto) throws Exception {
		return mvc.perform(post(uri).content(JSON.asJsonString(objeto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions sendPost(String token, String uri, Object objeto) throws Exception {
		return mvc.perform(post(uri).header("Authorization", "Bearer " + token).content(JSON.asJsonString(objeto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions sendPost(String token, String uri) throws Exception {
		return mvc.perform(post(uri).header("Authorization", "Bearer " + token));
	}

	private ResultActions sendPut(String token, String uri, Object objeto) throws Exception {
		return mvc.perform(put(uri).header("Authorization", "Bearer " + token).content(JSON.asJsonString(objeto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions sendGet(String uri) throws Exception {
		return mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions sendGet(String token, String uri) throws Exception {
		return mvc.perform(get(uri).header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions sendDelet(String token, String uri) throws Exception {
		return mvc.perform(delete(uri).header("Authorization", "Bearer " + token));
	}

	private ResultActions sendDelet(String uri) throws Exception {
		return mvc.perform(delete(uri));
	}

}
