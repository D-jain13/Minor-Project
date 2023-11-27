package com.dhairya.org.Ewallet.controller;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhairya.org.Ewallet.entity.Transaction;
import com.dhairya.org.Ewallet.entity.User;
import com.dhairya.org.Ewallet.entity.Wallet;
import com.dhairya.org.Ewallet.repository.TransactionRepo;
import com.dhairya.org.Ewallet.repository.UserRepo;
import com.dhairya.org.Ewallet.repository.WalletRepo;
import com.dhairya.org.Ewallet.service.TransactionService;

@Controller
public class EwalletController {
		
	@Autowired
	private UserRepo user_repo;
	
	@Autowired
	private WalletRepo wallet_repo;
	
	@Autowired
	private TransactionRepo t_repo;
	
	@Autowired 
	private TransactionService t_service;
	
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
		String bankAccountNumber = user.getBankAccountNumber();
		if(bankAccountNumber.length()<7) {
			
			return "invalid_bank";
		}
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
	public String showAddMoneyPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> user = user_repo.findByMobileNumber(username);
		
		model.addAttribute("balance",user.get().getAmount());
		
		
		return "addMoney";
	}
	@PostMapping("/add_money_to_wallet")
	public String add_Money(@RequestParam(name = "amount") float amount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> user = user_repo.findByMobileNumber(username);
		
		if(amount<=user.get().getAmount()) {
		Wallet wallet = wallet_repo.findByMobileNumber(username);
		float updated_balance = wallet.getBalance();
		updated_balance+=amount;
		if(updated_balance>=10000) {
			return "maxlimit"; 
		}
		wallet.setBalance(updated_balance);
	
		float bank_balance = user.get().getAmount();
		bank_balance = bank_balance-amount;
		final float b = bank_balance;
		user.ifPresent(u->u.setAmount(b));
		user.ifPresent(user_repo::save);
		
		Transaction t = new Transaction();
		t.setUserId(username);
		t.setMessage("Added Money");
		t.setAmount(amount);
		t.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));	
		
		t_repo.save(t);
		}
		else {
			return "insuffucient_fund";
		}
		return "redirect:dashboard";
	}
	@GetMapping("/listtransaction")
	public String listTransactions(Model model) {	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		List<Transaction> transaction = t_service.getTransactionByUserID(username);
		model.addAttribute("data",transaction);
		
		return "transaction";
	}
	
	@PostMapping("/transfertoUser")
	public String transfer_to_User(@RequestParam(name = "mobileNumber") String mobileNumber,@RequestParam(name = "amount") float amount) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
	
		Wallet transferer = wallet_repo.findByMobileNumber(username);
		
		if(amount<=transferer.getBalance()) {
			Wallet transferee = wallet_repo.findByMobileNumber(mobileNumber);
			if(transferee==null) {
				return "user_not_found";
			}
			if(username.equals(mobileNumber)) {
				return "self_transfer";
			}
			float balance_of_transferer = transferer.getBalance();
			balance_of_transferer = balance_of_transferer-amount;
			transferer.setBalance(balance_of_transferer);
			Transaction t_payer = new Transaction();
			t_payer.setAmount(amount);
			t_payer.setMessage("Transfered to " + mobileNumber);
			t_payer.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			t_payer.setUserId(username);
			t_repo.save(t_payer);
			float balance_of_trasferee = transferee.getBalance();
			balance_of_trasferee = balance_of_trasferee+amount;
			transferee.setBalance(balance_of_trasferee);
			Transaction t_payee = new Transaction();
			t_payee.setAmount(amount);
			t_payee.setMessage("Recieved from " +username);
			t_payee.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			t_payee.setUserId(mobileNumber);
			t_repo.save(t_payee);
			
			
			wallet_repo.save(transferer);
			wallet_repo.save(transferee);
		}
		
		else {
			return "insuffucient_fund";
		}
		return "redirect:dashboard";
	}
	@GetMapping("/transferMoney")
	public String transfer_money() {
		return "transfer";
	}
}
