package rede.social.nester.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
		if (verificaEmailExistente(usuarioEntity.getEmail())) {
			usuarioEntity.setSenha(criptografarSenha(usuarioEntity.getSenha()));
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
	public UsuarioEntity atualizarUsuario(UsuarioEntity usuarioEntity) {
		usuarioEntity.setSenha(criptografarSenha(usuarioEntity.getSenha()));
		return usuarioRepository.save(usuarioEntity);
	}

	@Transactional
	public void criarUsuarioAdminAoIniciarAplicacao(UsuarioEntity usuario) {
		if (verificaEmailExistente(usuario.getEmail())) {
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			usuarioRepository.save(usuario);
		}
	}

	// Metodos auxiliares
	
	private boolean verificaEmailExistente(String email) {
		UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email);
		if (usuarioEntity == null) {
			return true;
		}
		return false;
	}

	private String criptografarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}

	public List<UsuarioEntity> buscaUsuarioPor(String por) {
		PageRequest page = PageRequest.of(0, 10);
		return usuarioRepository.findByDadosCompletosContains(por, page);
	}
}
