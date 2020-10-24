package es.uv.tfm.userservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.repository.UserRepository;
import es.uv.tfm.userservice.services.UserService;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findByUsername(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				CustomUserDetailsService.getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
	
//	@Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//        User user = userRepository.getUserByUsername(username);
//         
//        if (user == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//         
//        //return new UserDetailsImpl(user);
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//		CustomUserDetailsService.getAuthorities(user));
//    }
}