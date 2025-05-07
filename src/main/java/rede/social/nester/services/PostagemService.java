package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;
import rede.social.nester.repositories.PostagemRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	@Transactional
	public PostagemEntity cadastraPostagem(PostagemEntity postagemEntity) {
		return postagemRepository.save(postagemEntity);
	}

	public List<PostagemEntity> buscaPostagemDoUsuario(UsuarioEntity usuarioEncontrado) {
		return postagemRepository.findAllByUsuario(usuarioEncontrado);
	}

	public PostagemEntity buscaPostagemPeloId(Long id) {
		return postagemRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Postagem não encontrada"));
	}

	@Transactional
	public void removerPostagem(UsuarioEntity usuarioEncontrado, PostagemEntity postagem) {
		if (usuarioEncontrado.getRole() == UsuarioEnum.ADMIN || usuarioEncontrado == postagem.getUsuario()) {
			postagemRepository.delete(postagem);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão necessária!");
		}
	}

	@Transactional
	public PostagemEntity atualizarPostagem(UsuarioEntity usuarioEncontrado, PostagemEntity postagem) {
		if (usuarioEncontrado.getRole() == UsuarioEnum.ADMIN || usuarioEncontrado == postagem.getUsuario()) {
			return postagemRepository.save(postagem);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão necessária!");
		}
	}
}
