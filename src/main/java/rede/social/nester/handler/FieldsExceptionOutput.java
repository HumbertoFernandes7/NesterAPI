package rede.social.nester.handler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldsExceptionOutput {

	private String name;
	private String message;
}
