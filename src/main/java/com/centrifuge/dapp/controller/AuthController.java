package com.centrifuge.dapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centrifuge.dapp.dao.RoleDao;
import com.centrifuge.dapp.dao.UserDao;
import com.centrifuge.dapp.entity.ERole;
import com.centrifuge.dapp.entity.Role;
import com.centrifuge.dapp.entity.User;
import com.centrifuge.dapp.payload.request.LoginRequest;
import com.centrifuge.dapp.payload.request.SignupRequest;
import com.centrifuge.dapp.payload.response.JwtResponse;
import com.centrifuge.dapp.payload.response.MessageResponse;
import com.centrifuge.dapp.security.jwt.JwtUtils;
import com.centrifuge.dapp.security.service.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDao userRepository;

	@Autowired
	RoleDao roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	User user;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
		if(user != null) {
			user.setToken(jwt);
			userRepository.save(user);
		}
			
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getName(), 
												 userDetails.getEmail(), 
												 userDetails.getUserRole(),
												 userDetails.getAuthorities()));
	}

	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
//		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User( signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getName());

		
		String strRoles = signUpRequest.getRole();
		
		Role userRole;
		

		if (strRoles == null) {
			userRole = roleRepository.findByName(ERole.USER.name())
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		}else if(strRoles.toUpperCase().equalsIgnoreCase("ADMINISTRATOR")) {
			userRole = roleRepository.findByName(ERole.ADMINISTRATOR.name())
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		}else {
			userRole =  roleRepository.findByName(ERole.USER.name())
			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		}
			

		user.setRole(userRole);
		userRepository.save(user);
		

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}