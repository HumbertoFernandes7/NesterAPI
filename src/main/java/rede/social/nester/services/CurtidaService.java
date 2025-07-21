package rede.social.nester.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.repositories.CurtidaRepository;

@Service
public class CurtidaService {

	@Autowired
	private CurtidaRepository curtidaRepository;

	@Transactional
	public Map<String, String> toggle(UsuarioEntity usuarioEncontrado, PostagemEntity postagemEncontrada) {
		CurtidaEntity curtidaEncontrada = curtidaRepository.findByPostagemAndUsuario(postagemEncontrada, usuarioEncontrado);
		Map<String, String> retorno = new HashMap<>();
		if(curtidaEncontrada == null) {
			CurtidaEntity curtida = new CurtidaEntity();
			curtida.setPostagem(postagemEncontrada);
			curtida.setUsuario(usuarioEncontrado);
			curtidaRepository.save(curtida);
			retorno.put("message", "Curtida realizada com sucesso!");
			return retorno;
		} else {
			curtidaRepository.delete(curtidaEncontrada);
			retorno.put("message", "Curtida apagada com sucesso!");
			return retorno;
		}
	}

	public List<CurtidaEntity> buscarMinhasCurtidas(UsuarioEntity usuarioEncontrado) {
		return curtidaRepository.findAllByUsuario(usuarioEncontrado);
	}
}
