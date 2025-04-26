package rede.social.nester.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rede.social.nester.converts.UsuarioConvert;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.repositories.UsuarioRepository;
import rede.social.nester.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioConvert usuarioConvert;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioOutput cadastrarUsuario(UsuarioInput usuarioInput){
        UsuarioEntity usuarioEntity = usuarioConvert.inputToEntity(usuarioInput);
        UsuarioEntity usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioEntity);
        UsuarioOutput usuarioOutput = usuarioConvert.entityToOutput(usuarioCadastrado);
        return usuarioOutput;
    }
}
