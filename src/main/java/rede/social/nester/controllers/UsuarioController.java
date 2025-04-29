package rede.social.nester.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rede.social.nester.converts.UsuarioConvert;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioConvert usuarioConvert;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public UsuarioOutput cadastrarUsuario(@RequestBody UsuarioInput usuarioInput){
        UsuarioEntity usuarioEntity = usuarioConvert.inputToEntity(usuarioInput);
        UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioEntity);
        UsuarioOutput usuarioOutput = usuarioConvert.entityToOutput(usuarioCadastrado);
        return usuarioOutput;
    }

    @DeleteMapping("/remover/{id}")
    public void removerUsuario(@PathVariable Long id){
        UsuarioEntity usuarioEncontrado= usuarioService.buscaUsuarioPorId(id);
        usuarioService.removerUsuario(usuarioEncontrado);
    }

    @GetMapping("/lista-todos")
    public List<UsuarioOutput> listarUsuarios(){
       List<UsuarioEntity> listaDeUsuarios = usuarioService.listarUsuarios();
       List<UsuarioOutput> usuarioOutputs = usuarioConvert.listEntityToListOutput(listaDeUsuarios);
       return usuarioOutputs;
    }
    
}
