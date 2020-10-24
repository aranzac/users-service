package es.uv.tfm.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public UserExistsException(String message) {
		super(message);
	}

}
