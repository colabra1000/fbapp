package com.centrifuge.dapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrifuge.dapp.dao.RoleDao;
import com.centrifuge.dapp.entity.Role;

@Service
public class RoleServiceImpl{

	@Autowired
	private RoleDao roleRepository;

	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	
	
}
