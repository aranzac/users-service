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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.repository.RoleRepository;
import es.uv.tfm.userservice.services.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class RoleServiceTest {

	
	@Autowired
	private RoleService roleService;
	
	@MockBean
	private  RoleRepository roleRepository;
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	public void getRolesTest() {
		when(roleRepository.findAll()).thenReturn(Stream.of(new Role(2, "ROLE_TEST")).collect(Collectors.toList()));
		assertEquals(1, roleService.getRoles().size());
	}
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	public void findByIdTest() {
		
		int id = 2;
		when(roleRepository.findById(id)).thenReturn((new Role(2, "ROLE_TEST")));	
		assertEquals(id, roleService.findById(id).getId());
	}
	
	@Test
	public void findByNameTest() {
		
		String name = "ROLE_TEST";
		when(roleRepository.findByName(name)).thenReturn((new Role(2, "ROLE_TEST")));	
		assertEquals(name, roleService.findByName(name).getName());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	public void createRoleTest() {
		Role role = new Role(2, "ROLE_TEST");
		when(roleRepository.save(role)).thenReturn(role); 
		assertEquals(role, roleService.createRole(role));
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	public void updateRoleTest() {
		Role role = new Role(2, "ROLE_TEST");
		when(roleRepository.save(role)).thenReturn(role); 
		role.setName("ROLE_TESTER");
		assertEquals("ROLE_TESTER", roleService.updateRole(2, role).getName());
	}
	
	
	@Test
	@WithMockUser(username = "test", roles = { "ADMIN" })
	public void deleteUserTest() {
		Role role = new Role(2, "ROLE_TEST");
		roleService.deleteRole(role);
		verify(roleRepository, times(1)).delete(role);
	}
}
