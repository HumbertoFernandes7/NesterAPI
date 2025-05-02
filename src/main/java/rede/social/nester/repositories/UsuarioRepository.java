package rede.social.nester.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
   
	UsuarioEntity findByEmail(String email);
	
}
