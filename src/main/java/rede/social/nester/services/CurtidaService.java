package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;
import rede.social.nester.repositories.CurtidaRepository;

@Service
public class CurtidaService {

	@Autowired
	private CurtidaRepository curtidaRepository;

	@Transactional
	public void curtirPostagem(PostagemEntity postagemEncontrada, UsuarioEntity usuarioEncontrado) {
		if (verificaSeUsuarioJaCurtiuPostagem(postagemEncontrada, usuarioEncontrado)) {
			CurtidaEntity curtida = new CurtidaEntity();
			curtida.setPostagem(postagemEncontrada);
			curtida.setUsuario(usuarioEncontrado);
			curtidaRepository.save(curtida);
		} else
			throw new BadRequestBussinessException("Usuario ja curtiu a publicação");
	}

	@Transactional
	public void removerCurtida(UsuarioEntity usuarioEncontrado, CurtidaEntity curtidaEncontrada) {
		if (curtidaEncontrada.getUsuario() == usuarioEncontrado) {
			curtidaRepository.delete(curtidaEncontrada);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão!");
		}
	}

	public CurtidaEntity buscaCurtidaPorID(Long id) {
		return curtidaRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Curtida não encontrada pelo id " + id));
	}

	public CurtidaEntity buscaCurtidaPelaPostagemAndUsuario(PostagemEntity postagemEncontrada,
			UsuarioEntity usuarioEncontrado) {
		CurtidaEntity curtidaEncontrada = curtidaRepository.findByPostagemAndUsuario(postagemEncontrada,
				usuarioEncontrado);
		if (curtidaEncontrada != null) {
			return curtidaEncontrada;
		} else {
			throw new NotFoundBussinessException(
					"Curtida não encontrada pelo usuario na postagem com id: " + postagemEncontrada.getId());
		}
	}

	// metodos auxiliares

	private boolean verificaSeUsuarioJaCurtiuPostagem(PostagemEntity postagem, UsuarioEntity usuario) {
		CurtidaEntity curtidaEncontrada = curtidaRepository.findByPostagemAndUsuario(postagem, usuario);
		if (curtidaEncontrada == null) {
			return true;
		} else
			return false;
	}

	public List<CurtidaEntity> buscarMinhasCurtidas(UsuarioEntity usuarioEncontrado) {
		return curtidaRepository.findAllByUsuario(usuarioEncontrado);	
	}
}
