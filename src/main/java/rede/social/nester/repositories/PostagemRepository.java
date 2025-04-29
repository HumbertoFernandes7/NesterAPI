package rede.social.nester.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rede.social.nester.entities.PostagemEntity;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {

}
