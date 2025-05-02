package rede.social.nester.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_postagem")
public class PostagemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mensagem")
	private String mensagem;

	@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CurtidaEntity> curtidas;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private UsuarioEntity usuario;
	
}
