package es.uv.tfm.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class IncorrectCredentialsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IncorrectCredentialsException(String message) {
		super(message);
	}
}
