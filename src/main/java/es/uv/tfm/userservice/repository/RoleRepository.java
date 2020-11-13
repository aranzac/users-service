package es.uv.tfm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uv.tfm.userservice.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findById(int id);

	Role findByName(String name);
	
	void deleteById(int id);

	
}
