package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
   
	UsuarioEntity findByEmail(String email);

	List<UsuarioEntity> findByDadosCompletosContains(String por, PageRequest page);
}
