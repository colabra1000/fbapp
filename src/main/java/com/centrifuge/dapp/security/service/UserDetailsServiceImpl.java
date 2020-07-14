package com.centrifuge.dapp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centrifuge.dapp.dao.UserDao;
import com.centrifuge.dapp.entity.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDao userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("User Not Found with the username: " + email));
		
		return UserDetailsImpl.build(user);
		
	}

}
