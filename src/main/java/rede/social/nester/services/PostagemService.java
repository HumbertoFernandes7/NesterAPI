package rede.social.nester.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.SeguidorEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.exceptions.NotFoundBusinessException;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;
import rede.social.nester.repositories.PostagemRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private FollowService followService;

	@Transactional
	public PostagemEntity cadastraPostagem(PostagemEntity postagemEntity) {
		postagemEntity.setDataPostagem(LocalDateTime.now());
		return postagemRepository.save(postagemEntity);
	}

	public List<PostagemEntity> buscaPostagemDoUsuario(UsuarioEntity usuarioEncontrado) {
		return postagemRepository.findAllByUsuarioOrderByDataPostagemDesc(usuarioEncontrado);
	}

	public PostagemEntity buscaPostagemPeloId(Long id) {
		return postagemRepository.findById(id)
				.orElseThrow(() -> new NotFoundBusinessException("Postagem não encontrada"));
	}

	@Transactional
	public void removerPostagem(UsuarioEntity usuarioEncontrado, PostagemEntity postagem) {
		if (verificaUsuarioAdminOuCriadorPublicacao(usuarioEncontrado, postagem)) {
			postagemRepository.delete(postagem);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão necessária!");
		}
	}

	@Transactional
	public PostagemEntity atualizarPostagem(UsuarioEntity usuarioEncontrado, PostagemEntity postagem) {
		if (verificaUsuarioAdminOuCriadorPublicacao(usuarioEncontrado, postagem)) {
			return postagemRepository.save(postagem);
		} else {
			throw new UnauthorizedAccessBussinessException("Usuario não tem permissão necessária!");
		}
	}

	public List<PostagemEntity> buscarPostagensParaForYou() {
		PageRequest page = PageRequest.of(0, 100, Sort.by("dataPostagem").descending());
		List<PostagemEntity> ultimasPublicações = postagemRepository.findUltimasPublicacoes(page);
		return ultimasPublicações;
	}

	public List<PostagemEntity> listarPostagemSeguidos(UsuarioEntity usuarioEncontrado) {
		List<PostagemEntity> postagens = new ArrayList<PostagemEntity>();

		List<SeguidorEntity> seguidosEncontrados = followService.listarFollowing(usuarioEncontrado);

		for (SeguidorEntity seguidorEntity : seguidosEncontrados) {
			postagens.addAll(postagemRepository.findAllByUsuarioOrderByDataPostagemDesc(seguidorEntity.getSeguido()));
		}
		return postagens;
	}

	private boolean verificaUsuarioAdminOuCriadorPublicacao(UsuarioEntity usuarioEncontrado, PostagemEntity postagem) {
		if (usuarioEncontrado.getRole() == UsuarioEnum.ADMIN || usuarioEncontrado == postagem.getUsuario()) {
			return true;
		} else {
			return false;
		}
	}
}
