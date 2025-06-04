package rede.social.nester.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import rede.social.nester.dtos.inputs.ResetSenhaInput;
import rede.social.nester.entities.SeguidorEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Value("${app.foto.perfil.caminho}")
	private String caminhoFotos;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FollowService followService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HashService hashService;

	@Autowired
	private FotoService fotoService;

	@Transactional
	public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
		if (verificaEmailExistente(usuarioEntity.getEmail())) {
			usuarioEntity.setSenha(criptografarSenha(usuarioEntity.getSenha()));
			usuarioEntity.setRole(UsuarioEnum.USER);
			usuarioEntity.setNomeFotoPerfil("padrao.jpg");
			return usuarioRepository.save(usuarioEntity);
		} else {
			throw new BadRequestBussinessException("Email já cadastrado no sistema!");
		}
	}

	public UsuarioEntity buscaUsuarioPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Usuario não encontrado!"));
	}

	public UsuarioEntity buscaUsuarioPorEmail(String email) {
		return usuarioRepository.findUsuarioByEmail(email)
				.orElseThrow(() -> new NotFoundBussinessException("Email não encontrado!"));
	}

	public List<UsuarioEntity> buscaUsuarioPor(String por) {
		PageRequest page = PageRequest.of(0, 10);
		return usuarioRepository.findByDadosCompletosContains(por, page);
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

	public byte[] buscarFotoUsuario(UsuarioEntity usuarioEncontrado) {
		Path caminhoFoto = Paths.get(caminhoFotos + usuarioEncontrado.getNomeFotoPerfil());
		if (!Files.exists(caminhoFoto) || !Files.isRegularFile(caminhoFoto)) {
			throw new BadRequestBussinessException("Foto não encontrada");
		}
		try {
			return Files.readAllBytes(caminhoFoto);

		} catch (IOException e) {
			throw new BadRequestBussinessException("Erro ao ler foto: " + e);
		}
	}

	@Transactional
	public void atualizarFotoPerfil(MultipartFile arquivo, UsuarioEntity usuarioEncontrado) {
		try {
			if (!arquivo.isEmpty()) {
				fotoService.verificaSeFotoJPG(arquivo);
				byte[] bytes = arquivo.getBytes();
				String nomeFoto = String.valueOf(usuarioEncontrado.getEmail()) + "foto-perfil.jpg";
				Path caminho = Paths.get(caminhoFotos + nomeFoto);
				Files.write(caminho, bytes);
				usuarioEncontrado.setNomeFotoPerfil(nomeFoto);
				usuarioRepository.save(usuarioEncontrado);
			}
		} catch (IOException | InvalidPathException ex) {
			throw new BadRequestBussinessException("Erro ao salvar foto de perfil: " + ex);
		}
	}

	@Transactional
	public void criarUsuarioAdminAoIniciarAplicacao(UsuarioEntity usuario) {
		if (verificaEmailExistente(usuario.getEmail())) {
			usuario.setNomeFotoPerfil("padrao.jpg");
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			usuarioRepository.save(usuario);
		}
	}

	@Transactional
	public void recuperarSenha(UsuarioEntity usuarioEncontrado, String hash, ResetSenhaInput resetSenhaInput) {
		String senha = resetSenhaInput.getSenha();
		String repetirSenha = resetSenhaInput.getRepetirSenha();
		if (senha.equals(repetirSenha)) {
			usuarioEncontrado.setSenha(criptografarSenha(senha));
			usuarioRepository.save(usuarioEncontrado);
			hashService.deletarHash(hash);
		} else {
			throw new BadRequestBussinessException("Senha e Repetir senha são diferentes");
		}
	}

	public List<UsuarioEntity> recomendarUsuarios(UsuarioEntity usuarioEncontrado) {
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();
		usuarios.remove(usuarioEncontrado);

		Set<UsuarioEntity> usuariosSeguidos = followService.listarFollowing(usuarioEncontrado).stream()
				.map(SeguidorEntity::getSeguido).collect(Collectors.toSet());
		usuarios.removeIf(seguido -> usuariosSeguidos.contains(seguido));

		return usuarios.stream().limit(4).collect(Collectors.toList());
	}
	
	public Map<String, Integer> buscarQuantidadeSeguidoresESeguidos(UsuarioEntity usuarioEncontrado) {
		Map<String, Integer> resultado = new HashMap<>();
		List<SeguidorEntity> following = followService.listarFollowing(usuarioEncontrado);
		List<SeguidorEntity> myFollowers = followService.listarMyFollowers(usuarioEncontrado);
		resultado.put("seguindo", following.size());
		resultado.put("seguidores", myFollowers.size());
		return resultado;
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
}
