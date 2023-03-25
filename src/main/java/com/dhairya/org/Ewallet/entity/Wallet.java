package com.dhairya.org.Ewallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Wallet {
	
	@Id
	@Column(name="mobileNumber",unique = true,nullable=false)
	private String mobileNumber;
	
	@Column(name="balance")
	private int balance;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Wallet(String mobileNumber, int balance) {
		super();
		this.mobileNumber = mobileNumber;
		this.balance = balance;
	}
	
	public Wallet() {
		
	}

	@Override
	public String toString() {
		return "Wallet [mobileNumber=" + mobileNumber + ", balance=" + balance + "]";
	}
	
	
	
}
