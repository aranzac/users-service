//package es.uv.tfm.userservice.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import es.uv.tfm.userservice.entities.User;
//import es.uv.tfm.userservice.services.UserServiceImpl;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserServiceUnitTests {
//
//	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
//	
//	@Autowired
//	@InjectMocks
//	private UserServiceImpl userService = new UserServiceImpl();
//
//	@Mock
//	private UserRepository userRepository;
//	
//	
//	private User user = new User(9999, "prueba", "123456", "prueba@prueba", "enabled");
//	
////	@Before
////	public void setUp() throws Exception{
////		user = new User(9999, "prueba", "123456", "prueba@prueba", "enabled");
////		userServices = new UserServiceImpl();
////	}
//	
//	@Before
//	public void initMocks(){
//	    MockitoAnnotations.initMocks(this);
//	}
//	
//	@Test
//    public void findByIdTest() {
//		
//		userService.createUser(user);		
//		User result = userService.findById(user.getId());
//		assertThat(result).isNotNull();
////		Assert.assertNotNull(null);
//		//Assert.assertEquals(user.getUsername(), result.getUsername());
//    }
//	
//	
//	@Test
//    public void updateUserTest() {
//		userService.createUser(user);
//		
//		userService.updateUser(user);		
//		User result = userService.findById(9999);
//		Assert.assertEquals("pruebas", result.getUsername());
//    }
//	
//	
////	Book savedBook = bookRepository.save(defaultBook);
////	 
////    ResponseEntity<Book> bookResponseEntity = this.restTemplate.getForEntity("/books/" + savedBook.getId(), Book.class);
////
////    assertThat(bookResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
////    assertThat(bookResponseEntity.getBody().getId()).isEqualTo(savedBook.getId());
//
//	
//	
//	
//}
