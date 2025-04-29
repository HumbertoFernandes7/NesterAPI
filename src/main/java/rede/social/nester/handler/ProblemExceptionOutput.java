package rede.social.nester.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;

@Getter
public class ProblemExceptionOutput {

	private int statusCode;
	private String message;
	private Calendar dateTime = Calendar.getInstance();

	private List<FieldsExceptionOutput> campos;

	public ProblemExceptionOutput(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
		campos = new ArrayList<>();
	}

	public ProblemExceptionOutput(int statusCode, String message, List<FieldsExceptionOutput> campos) {
		this(statusCode, message);
		this.campos = campos;
	}
}
