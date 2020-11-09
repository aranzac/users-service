package es.uv.tfm.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.services.RoleService;
import es.uv.tfm.userservice.services.UserService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<>(roleService.getRoles(),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Role getRole(@PathVariable("id") int id) {
		try {
			return roleService.findById(id);
		} catch(ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	} 
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/name/{name}")
	public Role getRoleByName(@PathVariable("name") String name) {
		try {
			return roleService.findByName(name);
		} catch(ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	} 

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/")
	public  Role createRole(@RequestBody Role role) {
		return  roleService.createRole(role);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public  ResponseEntity<Object> addRole(@PathVariable("id") int id, @RequestParam("name") String name) {
		
		try {
			User user = userService.findById(id);
			Role role = roleService.findByName(name);
			user.addRole(role);
			return new ResponseEntity<Object>(userService.updateUser(user), HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Object>(roleService.deleteRole(id), HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}
