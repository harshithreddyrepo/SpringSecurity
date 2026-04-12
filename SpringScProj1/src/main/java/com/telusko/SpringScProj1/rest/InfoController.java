package com.telusko.SpringScProj1.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class InfoController 
{

	@GetMapping("/info")
	public String getTeluskoInfo()
	{
		return "Telusko is planning to launch Simplified DevOps course";
	}
	
	@GetMapping("/devops")
	public String getTeluskoInfoOnCourse()
	{
		return " Simplified DevOps course and it will for 2.5 months";
	}
}
