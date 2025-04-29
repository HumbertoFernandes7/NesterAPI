package rede.social.nester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/cadastrar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PostagemOutput cadastrarPostagem(@RequestBody @Valid PostagemInput postagemInput) {
		UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(postagemInput.getUsuarioId());
		PostagemEntity postagemEntity = postagemConvert.inputToEntity(postagemInput);
		postagemEntity.setUsuario(usuarioEncontrado);
		PostagemEntity postagemCadastrada = postagemService.cadastraPostagem(postagemEntity);
		return postagemConvert.entityToOutput(postagemCadastrada);

	}
}
