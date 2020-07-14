package com.centrifuge.dapp.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centrifuge.dapp.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);
	
//	Optional<User> findByUsername(String username);
//	BexistsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	
}
