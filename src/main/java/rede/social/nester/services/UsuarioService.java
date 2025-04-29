package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
		if (verificaEmailExistente(usuarioEntity.getEmail())) {
			return usuarioRepository.save(usuarioEntity);
		} else {
			throw new RuntimeException("Email já cadastrado no sistema!");
		}
	}

	public UsuarioEntity buscaUsuarioPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("usuario não encontrado!"));
	}

	public void removerUsuario(UsuarioEntity usuarioEncontrado) {
		usuarioRepository.delete(usuarioEncontrado);
	}

	public List<UsuarioEntity> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	public UsuarioEntity atualizarUsuario(UsuarioEntity usuarioInput) {
		return usuarioRepository.save(usuarioInput);
	}

	public boolean verificaEmailExistente(String email) {
		UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email);

		if (usuarioEntity == null) {
			return true;
		}
		return false;
	}
}
