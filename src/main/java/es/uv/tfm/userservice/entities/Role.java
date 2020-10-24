package es.uv.tfm.userservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "roles")
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name= "id") 
	private int id;
	
	@Column(name= "name", unique= true)
	private String name;
	
//	@JsonIgnore
//	@ManyToMany(cascade = {CascadeType.MERGE})
//	@JoinTable(name = "users_has_roles", joinColumns = @JoinColumn(name = "roles_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
//	//@ManyToMany(mappedBy="roles")
//	private List<User> users;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
//	
//	public void addUser(User user) {
//		this.users.add(user);
//	}
	
}
