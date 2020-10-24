package es.uv.tfm.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uv.tfm.userservice.entities.Role;
import es.uv.tfm.userservice.entities.User;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(String name);

}
