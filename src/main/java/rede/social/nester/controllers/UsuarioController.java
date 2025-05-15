package rede.social.nester.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/cadastrar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioOutput cadastrarUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEntity = usuarioConvert.inputToEntity(usuarioInput);
		UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioEntity);
		return usuarioConvert.entityToOutput(usuarioCadastrado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/remover/{id}")
	public void removerUsuario(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		usuarioService.removerUsuario(usuarioEncontrado);
	}

	@GetMapping("/listar-todos")
	public List<UsuarioOutput> listarUsuarios() {
		List<UsuarioEntity> listaDeUsuarios = usuarioService.listarUsuarios();
		return usuarioConvert.listEntityToListOutput(listaDeUsuarios);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/buscar/{id}")
	public UsuarioOutput buscarPorId(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		return usuarioConvert.entityToOutput(usuarioEncontrado);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/atualizar/{id}")
	public UsuarioOutput atualizarUsuarioPeloId(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		usuarioConvert.copiaInputparaEntity(usuarioEncontrado, usuarioInput);
		UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(usuarioEncontrado);
		return usuarioConvert.entityToOutput(usuarioAtualizado);
	}

	@PutMapping("/atualizar")
	public UsuarioOutput atualizarUsuarioPeloToken(@RequestBody @Valid UsuarioInput usuarioInput) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		usuarioConvert.copiaInputparaEntity(usuarioEncontrado, usuarioInput);
		UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(usuarioEncontrado);
		return usuarioConvert.entityToOutput(usuarioAtualizado);
	}

	@GetMapping("/buscar")
	public List<UsuarioOutput> buscaUsuarioPor(String por) {
		List<UsuarioEntity> usuariosEncontrado = usuarioService.buscaUsuarioPor(por);
		return usuarioConvert.listEntityToListOutput(usuariosEncontrado);
	}

	@PostMapping("/enviar-email")
	public void enviarEmailHashRedefinirSenha(@RequestBody @Valid EmailResetInput emailReset) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorEmail(emailReset.getEmail());
		emailService.enviarEmailResetSenha(usuarioEncontrado);
	}

	@PutMapping("/recuperar-senha/{hash}/{id}")
	public void recuperarSenhaComHash(@PathVariable String hash, @PathVariable Long id,
			@RequestBody @Valid ResetSenhaInput resetSenhaInput) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		hashService.validarHash(hash);
		usuarioService.recuperarSenha(usuarioEncontrado, hash, resetSenhaInput);
	}
}
