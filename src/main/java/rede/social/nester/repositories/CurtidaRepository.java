package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;

public interface CurtidaRepository extends JpaRepository<CurtidaEntity, Long> {

	CurtidaEntity findByPostagemAndUsuario(PostagemEntity postagem, UsuarioEntity usuario);

	List<CurtidaEntity> findAllByPostagem(PostagemEntity postagemEncontrada);

	List<CurtidaEntity> findAllByUsuario(UsuarioEntity usuarioEncontrado);

}
