package es.uv.tfm.userservice.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.exceptions.UserExistsException;
import es.uv.tfm.userservice.repository.RoleRepository;
import es.uv.tfm.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
    //@Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public User findById(int id) {
		
		try {
			return userRepository.findById(id);
		}
		catch(Exception e) {
			throw  new ResourceNotFoundException(": No user found with id " + id);
		}
	
	}

	@Override
    //@Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public User findByUsername(String username) {
		
		try {
			return userRepository.findByUsername(username);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("No user found with Username " + username);
		}
	
	}

	@Override
    //@Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public User findByEmail(String email) {
		
		try {
			return userRepository.findByEmail(email);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("No user found with Email " + email);
		}
	}

	@Override
	public User createUser(User user) {

		if((userRepository.findByUsername(user.getUsername()) != null) || (userRepository.findByEmail(user.getEmail()) != null))
			throw new UserExistsException("User already exists");
		
		Role role = roleRepository.findByName("ROLE_USER");

		if (role == null)
			throw new ResourceNotFoundException("Role not found");

		user.addRole(role);

		return userRepository.save(user);
	}

	@Override
    //@Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public User updateUser(int id, User user) {
		
		User oldUser = userRepository.findById(id);
//		oldUser.setUsername(user.getUsername());
//		oldUser.setEmail(user.getEmail());
//		oldUser.setPassword(user.getPassword());
//		oldUser.setState(user.getState());
		//oldUser.setRoles(user.getRoles());

		return userRepository.save(user);
		
//		try {
//			 userRepository.findById(user.getId());
//			 return userRepository.save(user);
//		}
//		catch(Exception e) {
//			throw new ResourceNotFoundException(": No user found with id " + user.getId());
//		}
	}

	@Override
    @Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public void deleteUserById(int id) {

		userRepository.deleteById(id);

//		try {
//			userRepository.deleteById(id);
//
//		}
//		catch(Exception ex){
//			System.out.println("eyy" + ex);
//		}
		// userRepository.deleteById(id).orElseThrow(() -> new
		// ResourceNotFoundException("No user found with id " + id));
	}
	
	@Override
    @Secured ({"ROLE_USER", "ROLE_ADMIN"})
	public void deleteUser(User user) {

		userRepository.delete(user);
	}

}
