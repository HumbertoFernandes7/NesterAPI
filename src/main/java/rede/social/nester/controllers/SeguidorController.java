package rede.social.nester.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rede.social.nester.converts.FollowConvert;
import rede.social.nester.dtos.outputs.SeguidorOutput;
import rede.social.nester.entities.SeguidorEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.FollowService;
import rede.social.nester.services.TokenService;



@RestController
@RequestMapping("/follow")
public class SeguidorController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private FollowService followService;
	
	@Autowired
	private FollowConvert followConvert;

	@PostMapping("/{seguidoId}")
	public ResponseEntity<String> toggleFollow(@PathVariable Long seguidoId) {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		boolean seguindo = followService.toggleFollow(usuarioEncontrado, seguidoId);
		String mensagem = seguindo ? "Seguido com sucesso." : "Deixou de seguir com sucesso.";
		return ResponseEntity.ok(mensagem);
	}
	
	@GetMapping("/listar-seguidores")
	public List<SeguidorOutput> listarFollowers() {
		UsuarioEntity usuarioEncontrado = tokenService.buscaUsuarioPeloToken();
		List<SeguidorEntity> seguidores = followService.listarFollowers(usuarioEncontrado);
		return followConvert.listEntityToListOutput(seguidores);
	}
}
