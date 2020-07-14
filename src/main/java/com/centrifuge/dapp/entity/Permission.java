package com.centrifuge.dapp.entity;

import javax.persistence.Entity;


@Entity
public class Permission extends BaseIdEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String permission_name) {
		this.name = permission_name;
	}
	
	

}
