package rede.social.nester.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import rede.social.nester.converts.PostagemConvert;
import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.outputs.PostagemOutput;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.PostagemService;
import rede.social.nester.services.TokenService;
import rede.social.nester.services.UsuarioService;

@RestController
@RequestMapping("/postagem")
public class PostagemController {

	@Autowired
	private PostagemConvert postagemConvert;

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/cadastrar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PostagemOutput cadastrarPostagem(@RequestBody @Valid PostagemInput postagemInput) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagemEntity = postagemConvert.inputToEntity(postagemInput);
		postagemEntity.setUsuario(usuarioEncontrado);
		PostagemEntity postagemCadastrada = postagemService.cadastraPostagem(postagemEntity);
		return postagemConvert.entityToOutput(postagemCadastrada);
	}
	
	@GetMapping("{id}")
	public PostagemOutput buscarPostagemPeloId(@PathVariable Long id) {
		PostagemEntity postagemEntity = postagemService.buscaPostagemPeloId(id);
		return postagemConvert.entityToOutput(postagemEntity);
	}

	@GetMapping("/usuario/{id}")
	public List<PostagemOutput> listarPostagemDoUsuarioPeloId(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		List<PostagemEntity> postagemEntity = postagemService.buscaPostagemDoUsuario(usuarioEncontrado);
		return postagemConvert.listEntityToListOutput(postagemEntity);
	}

	@GetMapping("/usuario")
	public List<PostagemOutput> listarPostagensDoUsuarioLogado() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		List<PostagemEntity> postagemEntity = postagemService.buscaPostagemDoUsuario(usuarioEncontrado);
	//	postagemService.verificarQuantidadeCurtidas(postagemEntity);
		return postagemConvert.listEntityToListOutput(postagemEntity);
	}

	@DeleteMapping("/remover/{id}")
	public void removerPostagemUsuarioLogado(@PathVariable Long id) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagem = postagemService.buscaPostagemPeloId(id);
		postagemService.removerPostagem(usuarioEncontrado, postagem);
	}

	@PutMapping("/atualizar/{id}")
	public PostagemOutput atualizarPostagem(@RequestBody @Valid PostagemInput postagemInput, @PathVariable Long id) {
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(id);
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		postagemEncontrada.setMensagem(postagemInput.getMensagem());
		PostagemEntity postagemAtualizada = postagemService.atualizarPostagem(usuarioEncontrado, postagemEncontrada);
		return postagemConvert.entityToOutput(postagemAtualizada);
	}

	@GetMapping("/foryou")
	public List<PostagemOutput> listarForYou() {
		List<PostagemEntity> postagensEncontras = postagemService.buscaPostagensParaForYou();
		return postagemConvert.listEntityToListOutput(postagensEncontras);

	}
}
