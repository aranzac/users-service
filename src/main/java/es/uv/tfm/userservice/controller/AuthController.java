package es.uv.tfm.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.uv.tfm.userservice.entities.AuthRequest;
import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.security.JwtResponse;
import es.uv.tfm.userservice.security.JwtUtil;
import es.uv.tfm.userservice.services.RoleService;
import es.uv.tfm.userservice.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(AuthenticationException.class)
	@PostMapping("/authenticate")
	public ResponseEntity<Object> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		List<Role> roles;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			roles = userService.findByUsername(authRequest.getUsername()).getRoles();

		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		} catch (AuthenticationException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
		}

		return ResponseEntity.ok(
				new JwtResponse(jwtUtil.generateToken(authRequest.getUsername()), authRequest.getUsername(), roles));
	}
}
