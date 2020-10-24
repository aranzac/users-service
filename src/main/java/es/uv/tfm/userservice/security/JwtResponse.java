package es.uv.tfm.userservice.security;

import java.util.List;


import es.uv.tfm.userservice.entities.Role;

public class JwtResponse {

	 private String token;
	  private String type = "Bearer";
	  private String username;
	  private List<Role> roles;
	  
	  
	  public JwtResponse(String accessToken, String username, List<Role> roles) {
	    this.token = accessToken;
	    this.username = username;
	    this.setRoles(roles);
	  }
	 
	  public String getAccessToken() {
	    return token;
	  }
	 
	  public void setAccessToken(String accessToken) {
	    this.token = accessToken;
	  }
	 
	  public String getTokenType() {
	    return type;
	  }
	 
	  public void setTokenType(String tokenType) {
	    this.type = tokenType;
	  }
	 
	  public String getUsername() {
	    return username;
	  }
	 
	  public void setUsername(String username) {
	    this.username = username;
	  }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	  

	}