package com.telusko.SpringScProj1.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.service.IUserService;
import com.telusko.SpringScProj1.util.JwtUtil;

@RestController
@RequestMapping("/public")
public class PublicController 
{
	@Autowired
	private IUserService service;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@PostMapping("/sign-up")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		user.setPassword(encoder.encode(user.getPassword()));
		return new ResponseEntity<>(service.registerNewUser(user), HttpStatus.CREATED);
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<String> loginUser(@RequestBody User user){
		try {
			Authentication authentication = authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
			);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String jwt = jwtUtil.generateToken(userDetails.getUsername());

			System.out.println("HELLO");
			return new ResponseEntity<>(jwt, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}
		
	}
	

	
}
