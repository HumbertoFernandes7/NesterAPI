package rede.social.nester.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rede.social.nester.dtos.inputs.PostagemInput;
import rede.social.nester.dtos.outputs.PostagemOutput;
import rede.social.nester.entities.PostagemEntity;

@Component
public class PostagemConvert {

	@Autowired
	private ModelMapper modelMapper;

	public PostagemEntity inputToEntity(PostagemInput postagemInput) {
		return modelMapper.map(postagemInput, PostagemEntity.class);
	}

	public PostagemOutput entityToOutput(PostagemEntity postagemCadastrada) {
		return modelMapper.map(postagemCadastrada, PostagemOutput.class);
	}

	public List<PostagemOutput> listEntityToListOutput(List<PostagemEntity> postagemEntity) {
		return postagemEntity.stream().map((postagem) -> entityToOutput(postagem)).collect(Collectors.toList());
	}
}