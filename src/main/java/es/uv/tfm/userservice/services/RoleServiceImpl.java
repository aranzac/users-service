package es.uv.tfm.userservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.exceptions.ResourceNotFoundException;
import es.uv.tfm.userservice.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	
	@Override
	@Secured("ROLE_ADMIN")
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Role updateRole(int id, Role role) {
		roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found with id " + id));
		
		role.setId(id);
		
		return roleRepository.save(role);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Boolean deleteRole(int id) {
		
		try {
			 roleRepository.deleteById(id);
				return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Secured("ROLE_ADMIN")
	public Role findById(int id) {
		return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found with id " + id));

	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("No user found with Name " + name));

	}

	@Override
	@Secured("ROLE_ADMIN")
	public List<Role> getRoles(){
		return roleRepository.findAll();
	}
	
	
}
