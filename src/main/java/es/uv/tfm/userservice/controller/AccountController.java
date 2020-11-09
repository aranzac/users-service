package es.uv.tfm.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.exceptions.UserExistsException;
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
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public User getAccount(@PathVariable("id") int id) {	
		try {
			return userService.findById(id);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{user}")
	public User getAccountByUsername(@PathVariable("user") String user) {	
		try {
			return userService.findByUsername(user);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")
	public  User createUser(@RequestBody User user) {
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);	
		user.setState("enabled");
		
		System.out.println(user.getId() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + user.getState() + " " + user.getRoles().size());
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
			
			if (user.getState().equalsIgnoreCase("enabled"))
				user.setState("disabled");
			
			else if (user.getState().equalsIgnoreCase("disabled"))
				user.setState("enabled");
			
			
			return userService.updateUser(user);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		System.out.println("en controller");
		userService.deleteUser(id);
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
}