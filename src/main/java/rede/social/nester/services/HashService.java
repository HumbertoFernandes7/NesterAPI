package rede.social.nester.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rede.social.nester.entities.SenhaResetHashEntity;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.repositories.SenhaResetHashRepository;

@Service
public class HashService {

	@Autowired
	private SenhaResetHashRepository senhaResetHashRepository;

	public void validarHash(String hash) {
		SenhaResetHashEntity hashEncontrado = senhaResetHashRepository.findByHash(hash);
		if (hashEncontrado != null && hashEncontrado.getDataExpiracao().isAfter(LocalDateTime.now())) {
			return;
		} else {
			throw new BadRequestBussinessException("Hash invalido ou expirado");
		}
	}

	public String criarHashResetEmail() {
		return UUID.randomUUID().toString();
	}

	public void deletarHash(String hash) {
		senhaResetHashRepository.deleteByHash(hash);
	}
}
