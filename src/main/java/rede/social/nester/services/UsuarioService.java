package rede.social.nester.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.repositories.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {

        return usuarioRepository.save(usuarioEntity);
    }

    public UsuarioEntity buscaUsuarioPorId(Long id)  {
       return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("usuario não encontrado!"));
    }


    public void removerUsuario(UsuarioEntity usuarioEncontrado) {
        usuarioRepository.delete(usuarioEncontrado);
    }

    public List<UsuarioEntity> listarUsuarios() {
    return usuarioRepository.findAll();
    }
}
