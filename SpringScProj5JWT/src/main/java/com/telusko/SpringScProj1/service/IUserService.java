package com.telusko.SpringScProj1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.dao.UserRepo;

@Service
public interface IUserService{

	User registerNewUser(User user);
	
	User getByName(String name);
	
    List<User> fetchAllUsers();
}
