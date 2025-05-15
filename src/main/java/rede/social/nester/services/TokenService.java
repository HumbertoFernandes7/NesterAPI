package rede.social.nester.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.repositories.UsuarioRepository;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public String gerarToken(UsuarioEntity usuario) {
		try {
			Algorithm algorith = Algorithm.HMAC256(secret);
			String token = JWT.create().withIssuer("nester-api").withSubject(usuario.getEmail())
					.withExpiresAt(geraDataExpiracaoToken()).withClaim("role", usuario.getRole().toString())
					.sign(algorith);
			return token;
		} catch (JWTCreationException exception) {
			throw new BadRequestBussinessException("Erro na geração do token: " + exception);
		}
	}

	public String validarToken(String token) {

		try {
			Algorithm algorith = Algorithm.HMAC256(secret);
			return JWT.require(algorith).withIssuer("nester-api").build().verify(token).getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}

	public UsuarioEntity buscaUsuarioPeloToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
			throw new BadRequestBussinessException("Erro ao encontrar usuario logado");
		}

		return usuarioRepository.findByEmail(auth.getName());
	}

	private Instant geraDataExpiracaoToken() {
		return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
	}
}
