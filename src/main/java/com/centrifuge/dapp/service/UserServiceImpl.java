package com.centrifuge.dapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrifuge.dapp.dao.UserDao;
import com.centrifuge.dapp.entity.User;


@Service
public class UserServiceImpl {


	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Autowired
	private UserDao userRepository;

	
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}


	public Optional<User> getUser(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}
	
	
}
