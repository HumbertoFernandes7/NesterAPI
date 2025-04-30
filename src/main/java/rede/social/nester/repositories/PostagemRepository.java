package rede.social.nester.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;

import java.util.List;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {

    List<PostagemEntity> findAllByUsuario(UsuarioEntity usuarioEncontrado);
}
