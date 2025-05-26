package rede.social.nester.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.SeguidorEntity;
import rede.social.nester.entities.SeguidorId;
import rede.social.nester.entities.UsuarioEntity;

public interface SeguidorRepository extends JpaRepository<SeguidorEntity, SeguidorId> {

	List<SeguidorEntity> findBySeguidor(UsuarioEntity usuario);

	List<SeguidorEntity> findBySeguido(UsuarioEntity usuario);
}
