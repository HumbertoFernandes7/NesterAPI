package rede.social.nester.handler;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import rede.social.nester.exceptions.BadRequestBussinessException;
import rede.social.nester.exceptions.NotFoundBussinessException;
import rede.social.nester.exceptions.UnauthorizedAccessBussinessException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestBussinessException.class)
	public final ResponseEntity<ProblemExceptionOutput> handlerBadRequestBussinessException(
			BadRequestBussinessException ex, WebRequest request) {
		ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundBussinessException.class)
	public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBussinessException(NotFoundBussinessException ex,
			WebRequest request) {
		ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedAccessBussinessException.class)
	public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBussinessException(
			UnauthorizedAccessBussinessException ex, WebRequest request) {
		ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.UNAUTHORIZED);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		BindingResult bindingResult = ex.getBindingResult();

		List<FieldsExceptionOutput> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {

			String message = objectError.getDefaultMessage();
			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			message = message.replace("{1}", name);

			return FieldsExceptionOutput.builder().name(name).message(message).build();
		}).collect(Collectors.toList());

		ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.BAD_REQUEST.value(), detail,
				problemObjects);

		return new ResponseEntity<Object>(problema, HttpStatus.BAD_REQUEST);

	}
}
