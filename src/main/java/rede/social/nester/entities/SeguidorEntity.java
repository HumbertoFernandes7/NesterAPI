package rede.social.nester.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_seguidores")
public class SeguidorEntity {

	@EmbeddedId
	private SeguidorId id;

	@ManyToOne
	@MapsId("seguidorId")
	@JoinColumn(name = "seguidor_Id")
	private UsuarioEntity seguidor;

	@ManyToOne
	@MapsId("seguidoId")
	@JoinColumn(name = "seguido_Id")
	private UsuarioEntity seguido;

	@Column(name = "dataSeguimento")
	private LocalDateTime dataSeguimento;
}
