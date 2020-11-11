package es.uv.tfm.userservice.service;

import static org.junit.Assert.assertEquals;
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
	
		when(userRepository.findAll()).thenReturn(Stream.of(new User(9999, "prueba", "123456", "prueba@prueba", "enabled"), new User(9999, "prueba", "123456", "prueba@prueba", "enabled")).collect(Collectors.toList()));
		assertEquals(2, userService.getUsers().size());
	}
	
	
	@Test
	@WithMockUser
	public void findByIdTest() {
		int id = 9999;
		
		when(userRepository.findById(id))
		.thenReturn((new User(9999, "prueba", "123456", "prueba@prueba", "enabled")));	
		assertEquals(id, userService.findById(id).getId());

		//when(userRepository.findById(id)).thenReturn((User) Stream.of(new User(9999, "prueba", "123456", "prueba@prueba", "enabled"))));
	}
	
	
//	@Test
//	public void getUserbyAddressTest() {
//		String address = "Bangalore";
//		when(repository.findByAddress(address))
//				.thenReturn(Stream.of(new User(376, "Danile", 31, "USA")).collect(Collectors.toList()));
//		assertEquals(1, service.getUserbyAddress(address).size());
//	}
	
	
}