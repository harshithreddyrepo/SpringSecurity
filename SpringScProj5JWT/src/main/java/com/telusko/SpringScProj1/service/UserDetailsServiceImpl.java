package com.telusko.SpringScProj1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telusko.SpringScProj1.dao.User;
import com.telusko.SpringScProj1.dao.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByName(username);
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getName())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
	}

}
