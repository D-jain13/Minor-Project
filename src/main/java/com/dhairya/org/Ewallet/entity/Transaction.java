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
	
	@Column(name="message", nullable = false)
	private String message;

	@Column(name="amount", nullable = false)
	private double amount;
	
	@Column(name="timestamp",nullable = false)
	private String timestamp;

	public Transaction(Long id, String userId, String message, double amount, String timestamp) {
		super();
		this.id = id;
		this.message = message;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
