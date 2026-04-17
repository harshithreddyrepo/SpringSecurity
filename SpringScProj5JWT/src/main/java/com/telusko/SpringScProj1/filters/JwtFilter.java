package com.telusko.SpringScProj1.filters;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.telusko.SpringScProj1.service.UserDetailsServiceImpl;
import com.telusko.SpringScProj1.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private JwtUtil jwtUtil; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader = request.getHeader("Authorization");
		String token=null;
		String name=null;
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			token=authHeader.substring(7);
			name=jwtUtil.extractUserName(token);
		}
		if(name!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetailsServiceImpl userDetailsService = applicationContext.getBean(UserDetailsServiceImpl.class);
			UserDetails userDetails = userDetailsService.loadUserByUsername(name);
			
			if (jwtUtil.validateToken(token, userDetails.getUsername())) {

		        UsernamePasswordAuthenticationToken authToken =
		                new UsernamePasswordAuthenticationToken(
		                		userDetails.getUsername(),
		                        null,
		                        userDetails.getAuthorities()
		                );

		        SecurityContextHolder.getContext().setAuthentication(authToken);
		    }
		}
		
		filterChain.doFilter(request, response);
	}

}
