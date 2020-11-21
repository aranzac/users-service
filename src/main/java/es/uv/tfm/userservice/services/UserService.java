package es.uv.tfm.userservice.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.uv.tfm.userservice.entities.User;

public interface UserService{

	public User createUser(User user);

	public User updateUser( int id, User user);

	public void deleteUserById(int id);

	public void deleteUser(User user);

	public User findById(int id);

	public User findByUsername(String username);

	public User findByEmail(String email);

	public List<User> getUsers();

	
}
