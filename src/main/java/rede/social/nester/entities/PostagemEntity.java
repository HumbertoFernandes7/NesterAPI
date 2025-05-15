package rede.social.nester.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Column(name = "mensagem", length = 5000)
	private String mensagem;

	@Column(name = "dataPostagem")
	private LocalDateTime dataPostagem;

	@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CurtidaEntity> curtidas;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private UsuarioEntity usuario;

}
