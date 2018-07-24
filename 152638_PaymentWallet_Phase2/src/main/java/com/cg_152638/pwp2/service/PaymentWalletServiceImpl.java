package com.cg_152638.pwp2.service;

import java.math.BigDecimal;

import com.cg_152638.pwp2.beans.Customer;
import com.cg_152638.pwp2.exception.IPaymentWalletException;
import com.cg_152638.pwp2.exception.InsufficientBalanceException;
import com.cg_152638.pwp2.repo.IPaymentRepo;
import com.cg_152638.pwp2.repo.PaymentWalletRepoImpl;

public class PaymentWalletServiceImpl implements IPaymentService {

	private IPaymentRepo repo = null;

	public PaymentWalletServiceImpl() {
		/*
		 * Instantiating DAO Layer
		 */
		repo = new PaymentWalletRepoImpl();
	}

	@Override
	public Customer getCustomerDetails(String mobileNumber) {
		return repo.getCustomerDetails(mobileNumber);
	}

	@Override
	public boolean addCustomer(Customer newCustomer) {
		return repo.addCustomer(newCustomer);
	}

	@Override
	public void depositMoney(Customer customer, BigDecimal depositableAmount) {
		repo.depositMoney(customer, depositableAmount);
	}

	@Override
	public void withdrawMoney(Customer customer, BigDecimal withdrawableAmount) throws InsufficientBalanceException {
		/*
		 * Checking Balance if sufficient for Withdrawal or not
		 */
		int res = customer.getWalletBalance().subtract(withdrawableAmount).compareTo(new BigDecimal("1000"));
		if (res == 1) {
			repo.withdrawMoney(customer, withdrawableAmount);
		} else {
			throw new InsufficientBalanceException(IPaymentWalletException.MESSAGE5);
		}

	}

	@Override
	public StringBuilder printTransaction(String mobileNumber) {
		return repo.printTransaction(mobileNumber);
	}

	@Override
	public void fundTransfer(Customer customer, Customer receiverCustomer, BigDecimal transferableAmount)
			throws InsufficientBalanceException {
		/*
		 * Checking Balance if sufficient for Withdrawal or not PROJECT BY- VIKASH
		 * KUMAR(EMPID: 152638)
		 */
		int res = customer.getWalletBalance().subtract(transferableAmount).compareTo(new BigDecimal("1000"));
		if (res == 1) {
			repo.fundTransfer(customer, receiverCustomer, transferableAmount);
		} else {
			throw new InsufficientBalanceException(IPaymentWalletException.MESSAGE5);
		}
	}

}
