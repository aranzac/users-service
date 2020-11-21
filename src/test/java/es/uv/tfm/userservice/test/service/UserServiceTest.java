package es.uv.tfm.userservice.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.repository.UserRepository;
import es.uv.tfm.userservice.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest{
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private  UserRepository userRepository;
	
	@Test
	public void getUsersTest() {
		when(userRepository.findAll()).thenReturn(Stream.of(new User(9999, "prueba", "123456", "prueba@prueba", true), new User(9999, "prueba", "123456", "prueba@prueba", true)).collect(Collectors.toList()));
		assertEquals(2, userService.getUsers().size());
	}
	
	@Test
	@WithMockUser
	public void findByIdTest() {
		int id = 9999;
		when(userRepository.findById(id))
		.thenReturn((new User(9999, "prueba", "123456", "prueba@prueba", true)));	
		assertEquals(id, userService.findById(id).getId());
	}
	
	@Test
	@WithMockUser
	public void findByUsernameTest() {
		int id = 9999;
		String username = "prueba";
		when(userRepository.findByUsername(username)).thenReturn((new User(9999, "prueba", "123456", "prueba@prueba", true)));	
		assertEquals(id, userService.findByUsername(username).getId());
	}
	
	@Test
	@WithMockUser
	public void findByEmailTest() {
		int id = 9999;
		String email ="prueba@prueba";
		when(userRepository.findByEmail(email)).thenReturn((new User(9999, "prueba", "123456", "prueba@prueba",true)));	
		assertEquals(id, userService.findByEmail(email).getId());
	}
	
	
	@Test
	public void createUserTest() {
		User user = new User(9999, "prueba", "123456", "prueba@prueba", true);
		when(userRepository.save(user)).thenReturn(user); 
		assertEquals(user, userService.createUser(user));
	}
	
	
	@Test
	public void updateUserTest() {
		User user = new User(9999, "prueba", "123456", "prueba@prueba", true);
		when(userRepository.save(user)).thenReturn(user);
		user.setEmail("test@test");
		assertEquals("test@test", userService.updateUser(9999, user).getEmail());
	}
	
	
	@Test
	@WithMockUser
	public void deleteUserTest() {
		User user = new User(9999, "prueba", "123456", "prueba@prueba", true);
		userService.deleteUser(user);
		verify(userRepository, times(1)).delete(user);
	}
}


