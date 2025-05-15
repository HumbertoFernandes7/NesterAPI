package rede.social.nester.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.SenhaResetHashEntity;
import rede.social.nester.entities.UsuarioEntity;

public interface SenhaResetHashRepository extends JpaRepository<SenhaResetHashEntity, Long> {

	SenhaResetHashEntity findByHash(String hash);

	SenhaResetHashEntity findByUsuario(UsuarioEntity usuarioEncontrado);

	void deleteByHash(String hash);

}
