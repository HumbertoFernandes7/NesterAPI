package rede.social.nester.configs;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import rede.social.nester.entities.UsuarioEntity;
import rede.social.nester.enuns.UsuarioEnum;
import rede.social.nester.services.UsuarioService;

@Configuration
public class ExecutaAposInicioDaAplicacao implements ApplicationRunner {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNome("Humberto Fernandes");
		usuario.setEmail("humbertofernandes08@hotmail.com");
		usuario.setDataNascimento(LocalDate.of(2002, 8, 27));
		usuario.setRole(UsuarioEnum.ADMIN);
		usuario.setSenha("123456789");
		usuarioService.criarUsuarioAdminAoIniciarAplicação(usuario);
		
		UsuarioEntity usuario2 = new UsuarioEntity();
		usuario2.setNome("Vinicius Santana");
		usuario2.setEmail("email@hotmail.com");
		usuario2.setDataNascimento(LocalDate.of(2002, 8, 27));
		usuario2.setRole(UsuarioEnum.ADMIN);
		usuario2.setSenha("123456789");
		usuarioService.criarUsuarioAdminAoIniciarAplicação(usuario2);
	}

}
