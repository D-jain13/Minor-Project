package com.dhairya.org.Ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhairya.org.Ewallet.entity.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, String> {

	public Wallet findByMobileNumber(String mobileNumber);
}
