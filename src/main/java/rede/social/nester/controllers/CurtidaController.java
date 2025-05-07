package rede.social.nester.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.CurtidaService;
import rede.social.nester.services.PostagemService;
import rede.social.nester.services.UsuarioService;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    private PostagemService postagemService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CurtidaService curtidaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postagemId}/{usuarioId}")
    public void curtirPostagem(@PathVariable Long postagemId,@PathVariable Long usuarioId){
      PostagemEntity postagemEncontrada = postagemService.buscaPostagemPeloId(postagemId);
      UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(usuarioId);
      curtidaService.curtirPostagem(postagemEncontrada, usuarioEncontrado);
    }

    @DeleteMapping("/{id}")
    public void removerCurtida(@PathVariable Long id){
       CurtidaEntity curtidaEncontrada = curtidaService.buscaCurtidaPorID(id);
       curtidaService.removerCurtida(curtidaEncontrada);
    }


}
