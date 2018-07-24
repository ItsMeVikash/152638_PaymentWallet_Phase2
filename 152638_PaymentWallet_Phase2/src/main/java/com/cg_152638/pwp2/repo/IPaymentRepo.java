package com.cg_152638.pwp2.repo;

import java.math.BigDecimal;

import com.cg_152638.pwp2.beans.Customer;

public interface IPaymentRepo {

	public Customer getCustomerDetails(String mobileNumber);

	public boolean addCustomer(Customer newCustomer);

	public void depositMoney(Customer customer, BigDecimal depositableAmount);

	public void withdrawMoney(Customer customer, BigDecimal withdrawableAmount);

	public StringBuilder printTransaction(String mobileNumber);

	public void fundTransfer(Customer customer, Customer receiverCustomer, BigDecimal transferableAmount);

	

}
