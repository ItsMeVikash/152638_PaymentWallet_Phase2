package com.cg_152638.pwp2.beans;

import java.math.BigDecimal;

public class Wallet {
	private BigDecimal balance = new BigDecimal("0.00");

	public Wallet() {
		// TODO Auto-generated constructor stub
	}

	public Wallet(BigDecimal balance) {
		super();
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
