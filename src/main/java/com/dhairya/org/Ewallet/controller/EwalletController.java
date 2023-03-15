package com.dhairya.org.Ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dhairya.org.Ewallet.entity.User;
import com.dhairya.org.Ewallet.repository.UserRepo;

@Controller
public class EwalletController {
		
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/register.html")
	public String registration_page_loader() {
		return "register"; 	
	}
	
	@PostMapping("/loginagain")
	public String reglogin_method(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		return "relogin";
	}
	
	@PostMapping("/dashboard")
	public String hello(String mobileNumber,String password) {
		if(authenticate(mobileNumber, password)) {
			return "hello";
		}
		else {return "index";}
	}
	public boolean authenticate(String mobileNumber, String password) {
		User user = repo.findByMobileNumber(mobileNumber);
		if(user==null) {
			return false;
		}
		return passwordEncoder.matches(password, user.getPassword());
	}
}
