package rede.social.nester.converts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rede.social.nester.dtos.inputs.UsuarioInput;
import rede.social.nester.dtos.outputs.UsuarioOutput;
import rede.social.nester.entities.UsuarioEntity;

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
}
