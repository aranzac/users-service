package es.uv.tfm.userservice.services;

import java.util.List;
import java.util.Set;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;

public interface RoleService {

	public Role createRole(Role role);

	public Role updateRole(int id, Role role);

	public void deleteRoleById(int id);

	public void deleteRole(Role role);
	

	public Role findById(int id);

	public Role findByName(String name);

	public List<Role> getRoles();

}
