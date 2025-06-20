package rede.social.nester.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import rede.social.nester.converts.UsuarioConvert;
import rede.social.nester.dtos.inputs.EmailResetInput;
import rede.social.nester.dtos.inputs.ResetSenhaInput;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.EmailService;
import rede.social.nester.services.HashService;
import rede.social.nester.services.TokenService;
import rede.social.nester.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioConvert usuarioConvert;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private HashService hashService;

	// PostMapping

	@PostMapping("/cadastrar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioOutput cadastrarUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEntity = usuarioConvert.inputToEntity(usuarioInput);
		UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioEntity);
		return usuarioConvert.entityToOutput(usuarioCadastrado);
	}

	@PostMapping("/enviar-email")
	public void enviarEmailHashRedefinirSenha(@RequestBody @Valid EmailResetInput emailReset) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorEmail(emailReset.getEmail());
		emailService.enviarEmailResetSenha(usuarioEncontrado);
	}

	// GetMapping

	@GetMapping("/logado")
	public UsuarioOutput buscarDadosUsuariaLogado() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		return usuarioConvert.entityToOutput(usuarioEncontrado);
	}

	@GetMapping("/listar-todos")
	public List<UsuarioOutput> listarUsuarios() {
		List<UsuarioEntity> listaDeUsuarios = usuarioService.listarUsuarios();
		return usuarioConvert.listEntityToListOutput(listaDeUsuarios);
	}

	@GetMapping("/buscar/{id}")
	public UsuarioOutput buscarPorId(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		return usuarioConvert.entityToOutput(usuarioEncontrado);
	}

	@GetMapping("/recomendados")
	public List<UsuarioOutput> recomendarUsuarios() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		List<UsuarioEntity> usuariosEncontrados = usuarioService.recomendarUsuarios(usuarioEncontrado);
		List<UsuarioOutput> usuariosOutputs = usuarioConvert.listEntityToListOutput(usuariosEncontrados);
		return usuariosOutputs;
	}

	@GetMapping("/buscar")
	public List<UsuarioOutput> buscaUsuarioPor(String por) {
		List<UsuarioEntity> usuariosEncontrado = usuarioService.buscaUsuarioPor(por);
		return usuarioConvert.listEntityToListOutput(usuariosEncontrado);
	}

	@GetMapping("/minha/foto-perfil")
	public ResponseEntity<byte[]> buscarFotoPerfilUsuarioLogado() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		byte[] fotoEmBytes = usuarioService.buscarFotoUsuario(usuarioEncontrado);
		return ResponseEntity.ok().body(fotoEmBytes);
	}

	@GetMapping("/foto-perfil/{id}")
	public ResponseEntity<byte[]> buscarFotoPerfilPeloId(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		byte[] fotoEmBytes = usuarioService.buscarFotoUsuario(usuarioEncontrado);
		return ResponseEntity.ok().body(fotoEmBytes);
	}
	
	@GetMapping("/quantidade-seguidores-e-seguidos/{id}")
	public Map<String, Integer> buscarQuantidadeSeguidoresESeguidos(@PathVariable Long id){
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		return usuarioService.buscarQuantidadeSeguidoresESeguidos(usuarioEncontrado);
	}

	// PutMapping

	@PutMapping("/atualizar/foto-perfil/{id}")
	public void atualizarFotoPerfil(@PathVariable Long id, @RequestParam("file") MultipartFile arquivo) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		usuarioService.atualizarFotoPerfil(arquivo, usuarioEncontrado);
	}

	@PutMapping("/atualizar")
	public UsuarioOutput atualizarUsuarioPeloToken(@RequestBody @Valid UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		usuarioConvert.copiaInputparaEntity(usuarioEncontrado, usuarioInput);
		UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(usuarioEncontrado);
		return usuarioConvert.entityToOutput(usuarioAtualizado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/atualizar/{id}")
	public UsuarioOutput atualizarUsuarioPeloId(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		usuarioConvert.copiaInputparaEntity(usuarioEncontrado, usuarioInput);
		UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(usuarioEncontrado);
		return usuarioConvert.entityToOutput(usuarioAtualizado);
	}

	@PutMapping("/recuperar-senha/{hash}/{id}")
	public void recuperarSenhaComHash(@PathVariable String hash, @PathVariable Long id,
			@RequestBody @Valid ResetSenhaInput resetSenhaInput) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		hashService.validarHash(hash);
		usuarioService.recuperarSenha(usuarioEncontrado, hash, resetSenhaInput);
	}

	// DeleteMapping

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/remover/{id}")
	public void removerUsuario(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		usuarioService.removerUsuario(usuarioEncontrado);
	}
}
