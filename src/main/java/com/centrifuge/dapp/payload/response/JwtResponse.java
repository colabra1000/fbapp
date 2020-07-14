package com.centrifuge.dapp.payload.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String name;
	private String email;
	private String role;

	public JwtResponse(String accessToken, Long id, String name, String email, String role, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}
}
