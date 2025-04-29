package rede.social.nester.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConvert {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioEntity inputToEntity(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, UsuarioEntity.class);
    }

    public UsuarioOutput entityToOutput(UsuarioEntity usuarioCadastrado) {
        return modelMapper.map(usuarioCadastrado, UsuarioOutput.class);
    }

    public List<UsuarioOutput> listEntityToListOutput(List<UsuarioEntity> listaDeUsuarios) {
        return listaDeUsuarios.stream().map((usuario) -> entityToOutput(usuario)).collect(Collectors.toList());
    }

    public void copiaInputparaEntity(UsuarioEntity usuarioEncontrado, UsuarioInput usuarioInput) {
        modelMapper.map(usuarioInput, usuarioEncontrado);
    }
}
