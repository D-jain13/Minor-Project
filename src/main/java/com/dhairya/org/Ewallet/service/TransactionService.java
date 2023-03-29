package com.dhairya.org.Ewallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhairya.org.Ewallet.entity.Transaction;
import com.dhairya.org.Ewallet.repository.TransactionRepo;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepo transaction_repo;
	
	public List<Transaction> getTransactionByUserID(String userId){
		return transaction_repo.findByUserId(userId);
	}
}
