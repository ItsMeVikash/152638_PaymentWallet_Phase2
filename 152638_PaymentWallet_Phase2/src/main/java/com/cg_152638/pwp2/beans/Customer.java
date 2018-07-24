package com.cg_152638.pwp2.beans;

import java.math.BigDecimal;

public class Customer {
	private String mobileNumber;
	private String name;
	private Wallet wallet;
	private StringBuilder transaction;

	public StringBuilder getTransaction() {
		return transaction;
	}

	public void setTransaction(StringBuilder stringBuilder) {
		this.transaction = stringBuilder;
	}

	public Customer() {
		wallet = new Wallet();
	}


	public Customer(String mobileNumber, String name, Wallet wallet, StringBuilder transaction) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.wallet = wallet;
		this.transaction = transaction;
	}

	public BigDecimal getWalletBalance() {
		return wallet.getBalance();
	}

	public void setWalletBalance(BigDecimal balance) {
		wallet.setBalance(balance);
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
