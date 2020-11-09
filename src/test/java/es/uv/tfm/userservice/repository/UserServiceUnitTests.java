package es.uv.tfm.userservice.repository;



import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.services.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = DEFINED_PORT)
//@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTests {

	@InjectMocks
	private UserServiceImpl userService = new UserServiceImpl();

	@Mock
	private UserRepository userRepository;
	
	private User user = new User(9999, "prueba", "123456", "prueba@prueba", "enabled");
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		userService = new UserServiceImpl();
	}
	
	@Test
    public void findByIdTest() {
		
		userService.createUser(user);

		Mockito.when(userRepository.findById(9999)).thenReturn(Optional.ofNullable(user));
		
		User result = userService.findById(this.user.getId());
		Assert.assertEquals(this.user.getUsername(), result.getUsername());
    }
	
	
	@Test
    public void updateUserTest() {
		userService.createUser(user);
		
//		String nombre = "Test";
//		int id = this.user2.getId();
//		User user_aux = this.user;
//		user_aux.setUsername(nombre);
		//User result = userService.findById(id);
		//userRepository.save(this.user);
		userService.updateUser(this.user);
		Mockito.when(userRepository.findById(9999)).thenReturn(Optional.ofNullable(user));
		
		User result = userService.findById(9999);
		Assert.assertEquals("prueba", result.getUsername());
    }
	
	
//	Book savedBook = bookRepository.save(defaultBook);
//	 
//    ResponseEntity<Book> bookResponseEntity = this.restTemplate.getForEntity("/books/" + savedBook.getId(), Book.class);
//
//    assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(bookResponseEntity.getBody().getId()).isEqualTo(savedBook.getId());

	
	
	
}
