package es.uv.tfm.userservice.repository;



import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	private final UserServiceImpl userService = new UserServiceImpl();

	
	@Mock
	private UserRepository userRepository;
	
	//private static final Logger LOG = LoggerFactory.getLogger(UserServiceUnitTests.class);
	
	@Before(value = "")
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	
	
//	User user = User.builder().id(99999).username("prueba").password("123456").email("prueba@prueba").state("enabled").build();

	
	@Test
    public void findByIdTest() {
		
//		int id = 9999;
//        User user = new User(id, "prueba", "123456", "pruba@prueba", "enabled");
//        System.out.println(user.getId());
//        userService.createUser(user);
//        Mockito.when(userRepository.findById(9999)).thenReturn(Optional.ofNullable(user));
// 
//        User result = userService.findById(id);
//        
//        
//        Assert.assertEquals(user.getUsername(), result.getUsername());
		
		int id = 9999;
		User user;
		user = new User(id, "prueba", "123456", "pruba@prueba", "enabled");
		//User user2 = new User(1234567777, "pruebas", "123456", "prubas@prueba", "enabled");
		userService.createUser(user);
		//userRepository.save(user);
    }
	
	
//	Book savedBook = bookRepository.save(defaultBook);
//	 
//    ResponseEntity<Book> bookResponseEntity = this.restTemplate.getForEntity("/books/" + savedBook.getId(), Book.class);
//
//    assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(bookResponseEntity.getBody().getId()).isEqualTo(savedBook.getId());

	
	
	
}
