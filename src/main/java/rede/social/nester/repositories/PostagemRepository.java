package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {

	List<PostagemEntity> findAllByUsuario(UsuarioEntity usuarioEncontrado);

	@Query("SELECT p FROM PostagemEntity p ORDER BY p.dataPostagem DESC")
	List<PostagemEntity> findUltimasPublicacoes(PageRequest page);

	List<PostagemEntity> findAllByUsuarioOrderByDataPostagemDesc(UsuarioEntity usuarioEncontrado);
}
