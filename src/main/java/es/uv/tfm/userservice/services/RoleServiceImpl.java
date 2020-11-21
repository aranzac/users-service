package es.uv.tfm.userservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.repository.RoleRepository;
import es.uv.tfm.userservice.repository.UserRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	@Secured("ROLE_ADMIN")
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Role updateRole(int id, Role role) {
		try {
			roleRepository.findById(id);
			role.setId(id);
			return roleRepository.save(role);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("No role found with id: " + id);
		}		
		
		
	}
	
	@Override
    @Secured ("ROLE_ADMIN")
	public void deleteRole(Role role) {

		roleRepository.delete(role);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Role findById(int id) {
		try {
			return roleRepository.findById(id);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("No role found with id: " + id);
		}
	}

	@Override
	public Role findByName(String name) {
		
		try {
			System.out.println("service");
			return roleRepository.findByName(name);
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("No role found with name: " + name);
		}
	}

	@Override
	//@Secured({"ROLE_ADMIN"})
	public List<Role> getRoles(){
		return roleRepository.findAll();
	}

	@Override
	public void deleteRoleById(int id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
