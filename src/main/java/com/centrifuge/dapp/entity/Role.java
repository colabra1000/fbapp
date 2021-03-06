package com.centrifuge.dapp.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Role extends BaseIdEntity {
	
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Permission> permissions;

	public String getName() {
		return name;
	}

	public void setName(String role_name) {
		this.name = role_name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
