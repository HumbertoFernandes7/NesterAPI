package rede.social.nester.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rede.social.nester.converts.UsuarioConvert;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioConvert usuarioConvert;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UsuarioOutput cadastrarUsuario(@RequestBody @Valid UsuarioInput usuarioInput){
        UsuarioEntity usuarioEntity = usuarioConvert.inputToEntity(usuarioInput);
        UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioEntity);
        return usuarioConvert.entityToOutput(usuarioCadastrado);
    }

    @DeleteMapping("/remover/{id}")
    public void removerUsuario(@PathVariable Long id){
        UsuarioEntity usuarioEncontrado= usuarioService.buscaUsuarioPorId(id);
        usuarioService.removerUsuario(usuarioEncontrado);
    }

    @GetMapping("/listar-todos")
    public List<UsuarioOutput> listarUsuarios(){
       List<UsuarioEntity> listaDeUsuarios = usuarioService.listarUsuarios();
       return usuarioConvert.listEntityToListOutput(listaDeUsuarios);
    }

    @GetMapping("/buscar/{id}")
    public UsuarioOutput buscarPorId(@PathVariable Long id){
        UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
        return usuarioConvert.entityToOutput(usuarioEncontrado);
    }

    @PostMapping("/atualizar/{id}")
    public UsuarioOutput atualizaUsuario(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id){
       UsuarioEntity usuarioEncontrado = usuarioService.buscaUsuarioPorId(id);
       usuarioConvert.copiaInputparaEntity(usuarioEncontrado, usuarioInput);
       UsuarioEntity usuarioAtualizado = usuarioService.atualizarUsuario(usuarioEncontrado);
       return usuarioConvert.entityToOutput(usuarioAtualizado);
    }
    
}
