package rede.social.nester.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rede.social.nester.entities.CurtidaEntity;
import rede.social.nester.entities.PostagemEntity;
import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.repositories.CurtidaRepository;

@Service
public class CurtidaService {

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Transactional
    public void curtirPostagem(PostagemEntity postagemEncontrada, UsuarioEntity usuarioEncontrado) {

        if (verificaSeUsuarioJaCurtiuPostagem(postagemEncontrada, usuarioEncontrado)){
            CurtidaEntity curtida = new CurtidaEntity();
            curtida.setPostagem(postagemEncontrada);
            curtida.setUsuario(usuarioEncontrado);
            curtidaRepository.save(curtida);
        }else throw new BadRequestBussinessException("Usuario ja curtiu a publicação");

    }

    public boolean verificaSeUsuarioJaCurtiuPostagem(PostagemEntity postagem, UsuarioEntity usuario) {
        CurtidaEntity curtidaEncontrada = curtidaRepository.findByPostagemAndUsuario(postagem, usuario);
        if (curtidaEncontrada == null){
            return true;
        }else return false;
    }

    public CurtidaEntity buscaCurtidaPorID(Long id) {
       return curtidaRepository.findById(id).orElseThrow(() -> new NotFoundBussinessException("Curtida não encontrada pelo id " + id ));
    }

    @Transactional
    public void removerCurtida(CurtidaEntity curtidaEncontrada) {
        curtidaRepository.delete(curtidaEncontrada);
    }
}
