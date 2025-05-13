package rede.social.nester.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		postagemEntity.setDataPostagem(LocalDateTime.now());
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

	public List<PostagemEntity> buscaPostagensParaForYou() {
		PageRequest page = PageRequest.of(0, 50, Sort.by("dataPostagem").descending());
		List<PostagemEntity> ultimas50Publicações = postagemRepository.findUltimas50Publicacoes(page);
		return ultimas50Publicações;
	}
}
