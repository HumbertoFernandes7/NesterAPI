package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
		if (verificaEmailExistente(usuarioEntity.getEmail())) {
			String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioEntity.getSenha());
			usuarioEntity.setSenha(encryptedPassword);
			usuarioEntity.setRole(UsuarioEnum.USER);
			return usuarioRepository.save(usuarioEntity);
		} else {
			throw new BadRequestBussinessException("Email já cadastrado no sistema!");
		}
	}

	public UsuarioEntity buscaUsuarioPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("usuario não encontrado!"));
	}

	@Transactional
	public void removerUsuario(UsuarioEntity usuarioEncontrado) {
		usuarioRepository.delete(usuarioEncontrado);
	}

	public List<UsuarioEntity> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@Transactional
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

	@Transactional
	public void criarUsuarioAdminAoIniciarAplicação(UsuarioEntity usuario) {
		if (verificaEmailExistente(usuario.getEmail())) {
			String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(encryptedPassword);
			usuarioRepository.save(usuario);
		}
		return;
	}
}
