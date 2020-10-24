package es.uv.tfm.userservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "id")
	private int id;
	
	@Column(name= "username", unique= true)
	private String username;
	
	@Column(name= "password")
	private String password;
	
	@Column(name= "email", unique= true)
	private String email;
	
	@Column(name= "state")
	private String state;

	@ManyToMany(cascade = CascadeType.MERGE)
	//@JoinTable(name = "users_has_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	@JoinTable(name = "users_has_roles", joinColumns = {@JoinColumn(name="USERS_ID", referencedColumnName = "ID")}, inverseJoinColumns= {@JoinColumn(name="ROLES_ID", referencedColumnName="ID")})
	private List<Role> roles;
	
	
	public User() {
		super();
		this.roles = new ArrayList<Role>();
	}
	
	
	
	public User(int id, String username, String password, String email, String state) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.state = state;
		this.roles = new ArrayList<Role>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}



	public User(int id, String username, String password, String email, String state, List<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.state = state;
		this.roles = roles;
	}



}
