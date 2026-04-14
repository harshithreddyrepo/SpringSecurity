package com.telusko.SpringScProj1.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.service.IUserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IUserService service;
	
	@GetMapping("/get-user-list")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> userList = service.fetchAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
}
