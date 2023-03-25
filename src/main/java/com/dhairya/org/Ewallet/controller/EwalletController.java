package com.dhairya.org.Ewallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dhairya.org.Ewallet.entity.User;
import com.dhairya.org.Ewallet.entity.Wallet;
import com.dhairya.org.Ewallet.repository.UserRepo;
import com.dhairya.org.Ewallet.repository.WalletRepo;

@Controller
public class EwalletController {
		
	@Autowired
	private UserRepo user_repo;
	
	@Autowired
	private WalletRepo wallet_repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String showlogin() {
		return "login";
	}

	@GetMapping("/register.html")
	public String showRegistration() {
		return "register"; 	
	}
	
	
	@PostMapping("/loginagain")
	public String reglogin_method(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user_repo.save(user);
		String mob = user.getMobileNumber();
		Wallet wallet = new Wallet(mob, 0);
		wallet_repo.save(wallet);
		return "relogin";
	}
	
	@GetMapping("/dashboard")
	public String showDashBoard() {
		return "dashboard";
	}
}
