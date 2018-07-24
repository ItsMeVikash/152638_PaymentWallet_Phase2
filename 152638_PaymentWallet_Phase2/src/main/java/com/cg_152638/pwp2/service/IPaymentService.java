package com.cg_152638.pwp2.service;

import java.math.BigDecimal;

import com.cg_152638.pwp2.beans.Customer;
import com.cg_152638.pwp2.exception.InsufficientBalanceException;

public interface IPaymentService {

	public Customer getCustomerDetails(String mobileNumber);

	public boolean addCustomer(Customer newCustomer);

	public void depositMoney(Customer customer, BigDecimal depositableAmount);

	public void withdrawMoney(Customer customer, BigDecimal withdrawableAmount) throws InsufficientBalanceException;

	public StringBuilder printTransaction(String mobileNumber);

	public void fundTransfer(Customer customer, Customer receiverCustomer, BigDecimal transferableAmount) throws InsufficientBalanceException;

	
}
