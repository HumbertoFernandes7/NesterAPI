package rede.social.nester.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rede.social.nester.dtos.outputs.CurtidaOutput;
import rede.social.nester.entities.CurtidaEntity;

@Component
public class CurtidaConvert {

	@Autowired
	private ModelMapper modelMapper;

	public CurtidaOutput entityToOutput(CurtidaEntity curtidas) {
		return modelMapper.map(curtidas, CurtidaOutput.class);
	}

	public List<CurtidaOutput> listEntityToListOutput(List<CurtidaEntity> curtidas) {
		return curtidas.stream().map((curtida) -> entityToOutput(curtida)).collect(Collectors.toList());
	}
}
