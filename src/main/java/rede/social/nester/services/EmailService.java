package rede.social.nester.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import rede.social.nester.entities.SenhaResetHashEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.repositories.SenhaResetHashRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SenhaResetHashRepository senhaResetHashRepository;

	@Autowired
	private EntityManager manager;

	@Transactional
	public void enviarEmailResetSenha(UsuarioEntity usuario) {

		try {
			SenhaResetHashEntity existente = senhaResetHashRepository.findByUsuario(usuario);
			String hashParaEnviar;
			// Se não existe hash OU ele já expirou → criar novo
			if (existente == null || !existente.getDataExpiracao().isAfter(LocalDateTime.now())) {
				// Deleta o expirado, se houver
				if (existente != null) {
					senhaResetHashRepository.delete(existente);
					manager.flush();
				}
				// Cria novo hash
				hashParaEnviar = criarHashResetEmail();
				SenhaResetHashEntity novo = new SenhaResetHashEntity();
				novo.setUsuario(usuario);
				novo.setHash(hashParaEnviar);
				novo.setDataExpiracao(LocalDateTime.now().plusHours(1));
				senhaResetHashRepository.save(novo);

			} else {
				// Hash existe e ainda é válido → reutiliza
				hashParaEnviar = existente.getHash();
			}

			// Envia sempre o e-mail com o hash correto
			enviarEmail(usuario.getEmail(), "Redefinição de senha",
					"Seu código para redefinir a senha: " + hashParaEnviar);

		} catch (Exception e) {
			throw new BadRequestBussinessException("Erro ao enviar e-mail, tente novamente mais tarde!");
		}
	}

//	private boolean validarHashExistente(String hash) {
//		SenhaResetHashEntity hashEncontrado = senhaResetHashRepository.findByHash(hash);
//		LocalDateTime now = LocalDateTime.now();
//		if (hashEncontrado != null && hashEncontrado.getDataExpiracao().isAfter(now)) {
//			return true;
//		} else {
//			return false;
//		}
//	}	

	private void enviarEmail(String destino, String assunto, String texto) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(destino);
		msg.setSubject(assunto);
		msg.setText(texto);
		mailSender.send(msg);
	}

	private String criarHashResetEmail() {
		return UUID.randomUUID().toString();
	}
}
