package com.dhairya.org.Ewallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.org.Ewallet.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String>{

	 List<Transaction> findByUserId(String userId); 
}
