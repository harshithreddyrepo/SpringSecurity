package com.telusko.SpringScProj1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.dao.UserRepo;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepo repo;
	
	public User registerNewUser(User user) {
		return repo.save(user);
	}
	
	@Override
	public User getByName(String name) {
		User user = repo.findByName(name);
		return user;
    
	}

	@Override
	public List<User> fetchAllUsers() {
		List<User> all = repo.findAll();
		return all;
	}
}
