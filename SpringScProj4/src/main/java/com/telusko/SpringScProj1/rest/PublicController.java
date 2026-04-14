package com.telusko.SpringScProj1.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.service.IUserService;

@RestController
@RequestMapping("/public")
public class PublicController 
{
	@Autowired
	private IUserService service;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		user.setPassword(encoder.encode(user.getPassword()));
		return new ResponseEntity<>(service.registerNewUser(user), HttpStatus.CREATED);
	}
	
}
