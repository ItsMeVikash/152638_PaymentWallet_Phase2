package com.cg_152638.pwp2.exception;

public class InsufficientBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsufficientBalanceException() {
		super();
	}
	public InsufficientBalanceException(String msg) {
		super(msg);
	}
}
