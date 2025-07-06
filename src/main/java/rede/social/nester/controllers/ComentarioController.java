package rede.social.nester.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rede.social.nester.converts.ComentarioConvert;
import rede.social.nester.dtos.inputs.ComentarioInput;
import rede.social.nester.dtos.outputs.ComentarioOutput;
import rede.social.nester.entities.ComentarioEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.ComentarioService;
import rede.social.nester.services.PostagemService;
import rede.social.nester.services.TokenService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private ComentarioConvert comentarioConvert;

	@PostMapping("/{idPostagem}")
	public ResponseEntity<ComentarioOutput> cadastrarComentario(@RequestBody @Valid ComentarioInput comentarioInput,
			@PathVariable Long idPostagem) {
		UsuarioEntity usuarioLogado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(idPostagem);
		ComentarioEntity comentario = comentarioConvert.inputToEntity(comentarioInput);
		ComentarioEntity comentarioCadastrado = comentarioService.cadastraComentario(comentario, usuarioLogado,
				postagemEncontrada);
		ComentarioOutput comentarioOutput = comentarioConvert.entityToOutput(comentarioCadastrado);
		return ResponseEntity.status(HttpStatus.CREATED).body(comentarioOutput);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ComentarioOutput> atualizarComentario(@PathVariable Long id,
			@RequestBody @Valid ComentarioInput comentarioInput) {
		UsuarioEntity usuarioLogado =  tokenService.buscaUsuarioPeloToken();
		ComentarioEntity comentarioEncontrado = comentarioService.buscaComentarioPeloId(id);
		comentarioConvert.copyInputToEntity(comentarioInput, comentarioEncontrado);
		ComentarioEntity comentarioAtualizado = comentarioService.atualizarComentario(comentarioEncontrado, usuarioLogado);
		ComentarioOutput comentarioOutput = comentarioConvert.entityToOutput(comentarioAtualizado);
		return ResponseEntity.ok(comentarioOutput);
	}

	@GetMapping("/{idPostagem}/listar-todos")
	public ResponseEntity<List<ComentarioOutput>> buscarTodosComentariosPublicacao(@PathVariable Long idPostagem) {
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(idPostagem);
		List<ComentarioEntity> comentariosEncontrados = comentarioService
				.buscaComentariosPelaPostagem(postagemEncontrada);
		List<ComentarioOutput> comentariosOutput = comentarioConvert.listEntityToListOutput(comentariosEncontrados);
		return ResponseEntity.ok(comentariosOutput);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComentarioOutput> buscaComentarioPeloId(@PathVariable Long id) {
		ComentarioEntity comentariosEncontrados = comentarioService.buscaComentarioPeloId(id);
		ComentarioOutput comentarioOutput = comentarioConvert.entityToOutput(comentariosEncontrados);
		return ResponseEntity.ok(comentarioOutput);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaComentario(@PathVariable Long id) {
		UsuarioEntity usuarioLogado = tokenService.buscaUsuarioPeloToken();
		ComentarioEntity comentariosEncontrados = comentarioService.buscaComentarioPeloId(id);
		comentarioService.deletarComentario(comentariosEncontrados, usuarioLogado);
		return ResponseEntity.noContent().build();
	}
}
