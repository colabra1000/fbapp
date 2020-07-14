package com.centrifuge.dapp.security.jwt;

import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.centrifuge.dapp.dao.UserDao;
import com.centrifuge.dapp.entity.User;
import com.centrifuge.dapp.security.service.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${dapp.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${dapp.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	private User user;
	
	@Autowired
	private UserDao userRepository;
	
	public String generateJwtToken(Authentication authentication) {
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		Claims claims = Jwts.claims();
		claims.put("id", userPrincipal.getId());
		claims.put("username", userPrincipal.getUsername());
		claims.put("role", userPrincipal.getUserRole());
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).addClaims(claims)
				.compact();
	}
	
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		
		String email = getUserNameFromJwtToken(authToken);
		user = userRepository.findByEmail(email).orElse(null);
		
		
			
		if(user == null || !user.getToken().trim().equals(authToken.trim())) {
			return false;
		}
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
