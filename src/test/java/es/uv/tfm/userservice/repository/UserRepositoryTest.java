package es.uv.tfm.userservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	public void findByUsernameTest() {
		
		
		User user = User.builder().id(99999).username("prueba").password("123456").email("prueba@prueba").state("enabled").build();
		userRepository.save(user);
		
//		List<User> users = userRepository.findByUsername("prueba");
//		
//		Optional<User> user = userRepository.findByUsername("prueba");
//		assertNotNull(user);
//		assertEquals(9999999, user.get().getId());
	}
	
}
