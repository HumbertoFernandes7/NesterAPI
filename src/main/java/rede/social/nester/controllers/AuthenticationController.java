package rede.social.nester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rede.social.nester.dtos.inputs.AuthInput;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;
import rede.social.nester.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid AuthInput authInput) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(authInput.getEmail(), authInput.getSenha());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			String token = tokenService.gerarToken((UsuarioEntity) auth.getPrincipal());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			throw new UnauthorizedAccessBussinessException("Email ou senha inválidos!");
		}
	}

}
