package rede.social.nester.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import rede.social.nester.dtos.inputs.ComentarioInput;
import rede.social.nester.dtos.outputs.ComentarioOutput;
import rede.social.nester.entities.ComentarioEntity;

@Component
public class ComentarioConvert {

	@Autowired
	private ModelMapper modelMapper;

	public ComentarioEntity inputToEntity(ComentarioInput comentarioInput) {
		return modelMapper.map(comentarioInput, ComentarioEntity.class);
	}

	public ComentarioOutput entityToOutput(ComentarioEntity comentarioEntity) {
		return modelMapper.map(comentarioEntity, ComentarioOutput.class);
	}

	public List<ComentarioOutput> listEntityToListOutput(List<ComentarioEntity> comentariosEncontrados) {
		return comentariosEncontrados.stream().map((comentario) -> entityToOutput(comentario))
				.collect(Collectors.toList());
	}

	public void copyInputToEntity(@Valid ComentarioInput comentarioInput, ComentarioEntity comentarioEncontrado) {
		modelMapper.map(comentarioInput, comentarioEncontrado);
	}
}
