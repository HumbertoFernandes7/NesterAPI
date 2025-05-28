package rede.social.nester.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rede.social.nester.converts.CurtidaConvert;
import rede.social.nester.dtos.outputs.CurtidaOutput;
import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.CurtidaService;
import rede.social.nester.services.PostagemService;
import rede.social.nester.services.TokenService;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

	@Autowired
	private PostagemService postagemService;

	@Autowired
	private CurtidaService curtidaService;

	@Autowired
	private CurtidaConvert curtidaConvert;

	@Autowired
	private TokenService tokenService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{postagemId}")
	public void curtirPostagem(@PathVariable Long postagemId) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(postagemId);
		curtidaService.curtirPostagem(postagemEncontrada, usuarioEncontrado);
	}

	@DeleteMapping("/{postagemId}")
	public void removerCurtida(@PathVariable Long postagemId) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(postagemId);
		CurtidaEntity curtidaEncontrada = curtidaService.buscaCurtidaPelaPostagemAndUsuario(postagemEncontrada,
				usuarioEncontrado);
		curtidaService.removerCurtida(usuarioEncontrado, curtidaEncontrada);
	}

	@GetMapping("/minhas")
	public List<CurtidaOutput> buscarMinhasCurtidas() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		List<CurtidaEntity> curtidas = curtidaService.buscarMinhasCurtidas(usuarioEncontrado);
		return curtidaConvert.listEntityToListOutput(curtidas);
	}

}
