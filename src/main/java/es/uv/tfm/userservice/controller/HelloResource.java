package es.uv.tfm.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/hello")
public class HelloResource {

	@GetMapping("/all")
	public ResponseEntity<String> hello() {
		try {
			return new ResponseEntity<>("ola", HttpStatus.OK); 
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found", e);
		}
	}
}
