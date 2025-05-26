package rede.social.nester.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rede.social.nester.dtos.outputs.SeguidorOutput;
import rede.social.nester.entities.SeguidorEntity;

@Component
public class FollowConvert {

	@Autowired
	private ModelMapper modelMapper;

	public SeguidorOutput entityToOutput(SeguidorEntity seguidores) {
		return modelMapper.map(seguidores, SeguidorOutput.class);
	}

	public List<SeguidorOutput> listEntityToListOutput(List<SeguidorEntity> seguidores) {
		return seguidores.stream().map(s -> (this.entityToOutput(s))).collect(Collectors.toList());
	}
}
