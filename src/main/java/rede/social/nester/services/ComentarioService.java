package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.ComentarioEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.exceptions.NotFoundBusinessException;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;
import rede.social.nester.repositories.ComentarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Transactional
	public ComentarioEntity cadastraComentario(ComentarioEntity comentario, UsuarioEntity usuarioLogado,
			PostagemEntity postagemEncontrada) {
		comentario.setPostagem(postagemEncontrada);
		comentario.setUsuario(usuarioLogado);
		return comentarioRepository.save(comentario);
	}

	@Transactional
	public ComentarioEntity atualizarComentario(ComentarioEntity comentarioEncontrado, UsuarioEntity usuarioLogado) {
		if (verificaUsuarioAdminOuCriadorComentario(usuarioLogado, comentarioEncontrado)) {
			return comentarioRepository.save(comentarioEncontrado);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão para atualizar esse comentario");
		}
	}

	public List<ComentarioEntity> buscaComentariosPelaPostagem(PostagemEntity postagemEncontrada) {
		return comentarioRepository.findAllByPostagem(postagemEncontrada);
	}

	public ComentarioEntity buscaComentarioPeloId(Long id) {
		return comentarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundBusinessException("Comentario não encontrado"));
	}

	@Transactional
	public void deletarComentario(ComentarioEntity comentario, UsuarioEntity usuarioLogado) {
		if (verificaUsuarioAdminOuCriadorComentario(usuarioLogado, comentario)) {
			comentarioRepository.delete(comentario);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão para remover esse comentario");
		}

	}

	private boolean verificaUsuarioAdminOuCriadorComentario(UsuarioEntity usuarioEncontrado,
			ComentarioEntity comentario) {
		if (usuarioEncontrado.getRole() == UsuarioEnum.ADMIN || usuarioEncontrado == comentario.getUsuario()) {
			return true;
		} else {
			return false;
		}
	}
}
