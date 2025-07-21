package rede.social.nester.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	
	@PostMapping("/{postagemId}/toggle")
	public ResponseEntity<Map<String, String>> toggleCurtida(@PathVariable Long postagemId) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(postagemId);
		Map<String, String> curtida = curtidaService.toggle(usuarioEncontrado, postagemEncontrada);
		return ResponseEntity.ok(curtida);
	}

	@GetMapping("/minhas")
	public List<CurtidaOutput> buscarMinhasCurtidas() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		List<CurtidaEntity> curtidas = curtidaService.buscarMinhasCurtidas(usuarioEncontrado);
		return curtidaConvert.listEntityToListOutput(curtidas);
	}
}
