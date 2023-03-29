package com.dhairya.org.Ewallet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name="userId", nullable = false)
	private String userId;
	
	@Column(name="amount", nullable = false)
	private double amount;
	
	@Column(name="timestamp",nullable = false)
	private LocalDateTime timestamp;

	public Transaction(Long id, String userId, double amount, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
