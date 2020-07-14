package com.centrifuge.dapp.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER')")
	public String userAccess(Principal principal) {
		return "user and administrator have access.. \n "+
				principal.getName()+ " is presently logged in";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public String adminAccess(Principal principal) {
		return "Only admin have access.. \n "+ 
				principal.getName()+  " is presently logged in";
	}
	
	@GetMapping("/public")
	public String publicAccess() {
		return "every body has access to this";
	}
	

}
