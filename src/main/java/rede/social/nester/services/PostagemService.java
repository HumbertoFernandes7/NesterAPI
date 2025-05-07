package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.NotFoundBussinessException;
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

	public void removerPostagem(PostagemEntity postagem) {
		postagemRepository.delete(postagem);
	}

	@Transactional
	public PostagemEntity atualizaPostagem(PostagemEntity postagemEncontrada) {
		return postagemRepository.save(postagemEncontrada);
	}
}
