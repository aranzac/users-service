package es.uv.tfm.userservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.exceptions.UserExistsException;
import es.uv.tfm.userservice.security.JwtUtil;
import es.uv.tfm.userservice.services.RoleService;
import es.uv.tfm.userservice.services.UserService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public User getAccountById(@PathVariable("id") int id) {

		try {
			return userService.findById(id);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	// Se comprueba que el usuario del token coincida con el usuario que se pide
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{user}")
	public User getAccountByUsername(HttpServletRequest httpServletRequest, @PathVariable("user") String user) {
		
		
		String jwt = httpServletRequest.getHeader("authorization");
		String username = getUserFromToken(jwt);
	
		
		System.out.println(httpServletRequest.getUserPrincipal().getName());
		System.out.println(httpServletRequest.isUserInRole("ROLE_ADMIN"));
		
		if(httpServletRequest.isUserInRole("ROLE_ADMIN") || username.equals(user)) {
			try {
				return userService.findByUsername(user);
			}catch(ResourceNotFoundException ex) {

				throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
			}
		}
		else if(!httpServletRequest.isUserInRole("ROLE_ADMIN")&& !username.equals(user)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene los permisos necesarios");

		}
		
		
		return null;
		
		
//		try {
//			
//			if(httpServletRequest.isUserInRole("ROLE_ADMIN") || username.equals(user))
//					return userService.findByUsername(user);
//			else if(!httpServletRequest.isUserInRole("ROLE_ADMIN")&& !username.equals(user))
//				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no tiene los permisos necesarios");
//			
//		} catch (ResourceNotFoundException ex) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
//		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")
	public User createUser(@RequestBody User user) {

		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		user.setState(true);

		System.out.println(user.getId() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword()
				+ " " + user.getState() + " " + user.getRoles().size());
		user.getRoles().forEach((x) -> {
			System.out.println("ROL: " + x.getName());
		});

		try {
			return userService.createUser(user);
		} catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/block/{id}")
	public User blockUser(@PathVariable("id") int id) {

		try {
			User user = userService.findById(id);

			if (user.getState())
				user.setState(false);

			else if (!user.getState())
				user.setState(true);

			return userService.updateUser(id, user);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteUser(HttpServletRequest httpServletRequest, @PathVariable("id") int id) {

		User user = userService.findById(id);
		String userToken = getUserFromToken(httpServletRequest.getHeader("authorization"));

		if (userToken.equals(user.getUsername())) {
			userService.deleteUser(userService.findById(id));
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
				"El usuario requerido no coincide con el registrado");

//		try {
//			System.out.println("intentando");
//			userService.deleteUser(id);
//			 
//		} catch (Exception ex) {
//			System.out.println("catch");
//
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
//		}
	}

	public String getUserFromToken(String header) {

		JwtUtil jwtUtil = new JwtUtil();

		String jwt = header.substring(7, header.length());
		return jwtUtil.extractUsername(jwt);
	}

}