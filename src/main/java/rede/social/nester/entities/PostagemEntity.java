package rede.social.nester.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
	
	@Column(name = "curtida")
	private int curtida;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private UsuarioEntity usuario;
	
}
