package rede.social.nester.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.repositories.PostagemRepository;

@Service
public class PostagemService {
	
	@Autowired
	private PostagemRepository postagemRepository;

	@Transactional
	public PostagemEntity cadastraPostagem(PostagemEntity postagemEntity) {
		return postagemRepository.save(postagemEntity);
	}

	
}
