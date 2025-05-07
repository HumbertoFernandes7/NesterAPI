package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {

    List<PostagemEntity> findAllByUsuario(UsuarioEntity usuarioEncontrado);
}
