package com.dhairya.org.Ewallet.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showDashBoard(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		Wallet wallet = wallet_repo.findByMobileNumber(username);
		model.addAttribute("balance", wallet.getBalance());
		return "dashboard";
	}
	
	@GetMapping("/addMoney")
	public String addMoney(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> user = user_repo.findByMobileNumber(username);
		
		model.addAttribute("balance",user.get().getAmount());
		
//		Wallet wallet = wallet_repo.findByMobileNumber(username);
//		float updated_balance = wallet.getBalance();
//		updated_balance+=amount;
//		
//		wallet.setBalance(updated_balance);
		
		return "addMoney";
	}
	
	@GetMapping("/listtransaction")
	public String listTransactions() {	
		return "transaction";
	}
	
	@GetMapping("/transferMoney")
	public String transfer_money() {
		return "transfer";
	}
}
