package rede.social.nester.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UsuarioEnum {

	ADMIN("admin"), USER("user");

	private String role;
}
