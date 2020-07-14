package com.centrifuge.dapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centrifuge.dapp.entity.Role;


@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
