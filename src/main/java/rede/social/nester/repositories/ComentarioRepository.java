package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.ComentarioEntity;
import rede.social.nester.entities.PostagemEntity;

public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

	List<ComentarioEntity> findAllByPostagem(PostagemEntity postagemEncontrada);

}
