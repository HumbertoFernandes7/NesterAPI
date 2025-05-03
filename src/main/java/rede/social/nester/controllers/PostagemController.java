package rede.social.nester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import rede.social.nester.converts.PostagemConvert;
import rede.social.nester.dtos.inputs.AtualizaPostagemInput;
import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.outputs.PostagemOutput;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.PostagemService;
import rede.social.nester.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/postagem")
public class PostagemController {

	@Autowired
	private PostagemConvert postagemConvert;

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/cadastrar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PostagemOutput cadastrarPostagem(@RequestBody @Valid PostagemInput postagemInput) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(postagemInput.getUsuarioId());
		PostagemEntity postagemEntity = postagemConvert.inputToEntity(postagemInput);
		postagemEntity.setUsuario(usuarioEncontrado);
		PostagemEntity postagemCadastrada = postagemService.cadastraPostagem(postagemEntity);
		return postagemConvert.entityToOutput(postagemCadastrada);
	}

	@GetMapping("/usuario/{id}")
	public List<PostagemOutput> listarPostagemDoUsuario(@PathVariable Long id){
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
		List<PostagemEntity> postagemEntity = postagemService.buscaPostagemDoUsuario(usuarioEncontrado);
		return postagemConvert.listEntityToListOutput(postagemEntity);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/remover/{id}")
	public void removerPostagem(@PathVariable Long id){
		PostagemEntity postagem = postagemService.buscaPostagemPeloId(id);
		postagemService.removerPostagem(postagem);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/atualizar/{id}")
	public PostagemOutput atualizarPostagem(@RequestBody @Valid AtualizaPostagemInput atualizaPostagemInput, @PathVariable Long id){
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(id);
		postagemEncontrada.setMensagem(atualizaPostagemInput.getMensagem());
		PostagemEntity postagemAtualizada = postagemService.atualizaPostagem(postagemEncontrada);
		return postagemConvert.entityToOutput(postagemAtualizada);
	}
}
