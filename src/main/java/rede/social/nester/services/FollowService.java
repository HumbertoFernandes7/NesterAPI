package rede.social.nester.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.SeguidorEntity;
import rede.social.nester.entities.SeguidorId;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.repositories.SeguidorRepository;

@Service
public class FollowService {

	@Autowired
	private SeguidorRepository seguidorRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Transactional
	public boolean toggleFollow(UsuarioEntity seguidor, Long seguidoId) {
		UsuarioEntity seguido = usuarioService.buscaUsuarioPorId(seguidoId);

		SeguidorId key = new SeguidorId(seguidor.getId(), seguido.getId());

		if (seguidorRepository.existsById(key)) {
			seguidorRepository.deleteById(key);
			return false;
		} else {
			SeguidorEntity follow = new SeguidorEntity();
			follow.setId(key);
			follow.setSeguidor(seguidor);
			follow.setSeguido(seguido);
			follow.setDataSeguimento(LocalDateTime.now());
			seguidorRepository.save(follow);
			return true;
		}
	}

	public List<SeguidorEntity> listarFollowing(UsuarioEntity usuarioEncontrado) {
		return seguidorRepository.findBySeguidor(usuarioEncontrado);
	}

	public List<SeguidorEntity> listarMyFollowers(UsuarioEntity usuarioEncontrado) {
		return seguidorRepository.findBySeguido(usuarioEncontrado);
	}

	public boolean verificarSeguidor(UsuarioEntity usuarioLogado, UsuarioEntity usuarioEncontrado) {
		return seguidorRepository.existsById(new SeguidorId(usuarioLogado.getId(), usuarioEncontrado.getId()));
	}
}
