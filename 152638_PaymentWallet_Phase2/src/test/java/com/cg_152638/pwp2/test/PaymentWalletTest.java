package com.cg_152638.pwp2.test;

import static org.junit.Assert.*;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import com.cg_152638.pwp2.beans.Customer;
import com.cg_152638.pwp2.beans.Wallet;
import com.cg_152638.pwp2.exception.InsufficientBalanceException;
import com.cg_152638.pwp2.exception.InvalidInputException;
import com.cg_152638.pwp2.service.IPaymentService;
import com.cg_152638.pwp2.service.IPaymentServiceValidation;
import com.cg_152638.pwp2.service.PaymentWalletServiceImpl;
import com.cg_152638.pwp2.service.PaymentWalletServiceValidation;

public class PaymentWalletTest {

	IPaymentService service = null;
	IPaymentServiceValidation serviceValidation = null;
	Customer customer1, customer2;

	/*
	 * Initializing All Required Fields
	 */
	@Before
	public void initData() {
		service = new PaymentWalletServiceImpl();
		serviceValidation = new PaymentWalletServiceValidation();
		customer1 = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("10100")),
				new StringBuilder("New Account"));
		customer2 = new Customer("8210403625", "Vishnu Kumar", new Wallet(new BigDecimal("20000")),
				new StringBuilder("New Account"));
		service.addCustomer(customer1);
		service.addCustomer(customer2);

	}

	/*
	 * Testing Menu Choice Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testMenuChoiceWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("10");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testMenuChoiceWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("");
		assertFalse(output);
	}

	@Test
	public void testMenuChoiceWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("1");
		assertTrue(output);
	}

	/*
	 * Testing MobileNumber Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testMobileNoWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("101235sd5");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testMobileNoWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("");
		assertFalse(output);
	}

	@Test
	public void testMobileNoWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("9852993617");
		assertTrue(output);
	}

	/*
	 * Testing Name Validation Method...PROJECT BY- VIKASH KUMAR(EMPID: 152638)
	 */
	@Test(expected = InvalidInputException.class)
	public void testNameWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("Vikash123");
		assertFalse(output);
	}

	@Test(expected = AssertionError.class)
	public void testNameWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("");
		assertFalse(output);
	}

	@Test
	public void testNameWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("Vikash Kumar");
		assertTrue(output);
	}

	/*
	 * Testing Amount Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testAmountWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("12sc.00");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testAmountWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("");
		assertFalse(output);
	}

	@Test
	public void testAmountWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("1000");
		assertTrue(output);
	}

	/*
	 * Testing GetCustomerDetails Method
	 */
	@Test
	public void testGetCustomerDetailsWithInvalidInput() {
		Customer output = service.getCustomerDetails("1236548996");
		assertNull(output);
	}

	@Test(expected = AssertionError.class)
	public void testGetCustomerDetailsWithEmptyInput() {
		Customer output = service.getCustomerDetails("");
		assertNull(output);
	}

	@Test
	public void testGetCustomerDetailsWithValidInput() {
		Customer output = service.getCustomerDetails("1231231231");
		assertNotNull(output);
	}

	/*
	 * Testing AddCustomer Method
	 */
	@Test(expected = AssertionError.class)
	public void testAddCustomerWithInvalidInput() {
		Customer customer = new Customer("", "Vikash Kumar", new Wallet(new BigDecimal("1010")), new StringBuilder(""));
		assertNull(service.addCustomer(customer));
	}

	@Test(expected = NumberFormatException.class)
	public void testAddCustomerWithEmptyInput() {
		Customer customer = new Customer("", "", new Wallet(new BigDecimal("")), new StringBuilder(""));
		assertNull(service.addCustomer(customer));
	}

	@Test(expected = AssertionError.class)
	public void testAddCustomerWithValidInput() {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		assertNull(service.addCustomer(customer));
	}

	/*
	 * Testing DepositMoney Method
	 */
	@Test(expected = NumberFormatException.class)
	public void testDepositMoneyWithInvalidInput() {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.depositMoney(customer, new BigDecimal("15sk"));
		assertNull(customer.getWalletBalance());
	}

	@Test(expected = NumberFormatException.class)
	public void testDepositMoneyWithEmptyInput() {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.depositMoney(customer, new BigDecimal(""));
		assertNull(customer.getWalletBalance());
	}

	@Test(expected = AssertionError.class)
	public void testDepositMoneyWithValidInput() {
		Customer customer = new Customer("1478523698", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.depositMoney(customer, new BigDecimal("1500"));
		assertNull(customer.getWalletBalance());
	}

	/*
	 * Testing WithdrawMoney Method PROJECT BY- VIKASH KUMAR(EMPID: 152638)
	 */
	@Test(expected = NumberFormatException.class)
	public void testWithdrawMoneyWithInvalidInput() throws InsufficientBalanceException {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.withdrawMoney(customer, new BigDecimal("15sk"));
		assertNull(customer.getWalletBalance());
	}

	@Test(expected = NumberFormatException.class)
	public void testWithdrawMoneyWithEmptyInput() throws InsufficientBalanceException {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.withdrawMoney(customer, new BigDecimal(""));
		assertNull(customer.getWalletBalance());
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawMoneyInsufficientBalance() throws InsufficientBalanceException {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.withdrawMoney(customer, new BigDecimal("1500"));
		assertNull(customer.getWalletBalance());
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawMoneyWithValidInput() throws InsufficientBalanceException {
		Customer customer = new Customer("9852993617", "Vikash Kumar", new Wallet(new BigDecimal("1010")),
				new StringBuilder(""));
		service.withdrawMoney(customer, new BigDecimal("100"));
		assertNull(customer.getWalletBalance());
	}

	/*
	 * Testing PrintingTransaction Method
	 */
	@Test(expected = NullPointerException.class)
	public void testPrintTransactionWithInvalidInput() {
		StringBuilder output = service.printTransaction("1478523698");
		assertNull(output);
	}

	@Test(expected = AssertionError.class)
	public void testPrintTransactionWithEmptyInput() {
		StringBuilder output = service.printTransaction("");
		assertNull(output);
	}

	@Test
	public void testPrintTransactionWithValidInput() {
		StringBuilder output = service.printTransaction("1231231231");
		assertNotNull(output);
	}

	/*
	 * Testing FundTransfer Method
	 */
	@Test(expected = NumberFormatException.class)
	public void testFundTransferWithEmptyInput() throws InsufficientBalanceException {
		service.fundTransfer(customer2, customer1, new BigDecimal(""));
		assertNull(customer1.getWalletBalance());
	}

	@Test(expected = AssertionError.class)
	public void testFundTransferInsufficientBalance() throws InsufficientBalanceException {
		service.fundTransfer(customer2, customer1, new BigDecimal("1500"));
		assertNull(customer1.getWalletBalance());
	}

	@Test
	public void testFundTransferWithValidInput() throws InsufficientBalanceException {
		service.fundTransfer(customer1, customer2, new BigDecimal("1000"));
		assertNotNull(customer1.getWalletBalance());
	}
	/*
	 * All Service Class Methods Are Tested.PROJECT BY-VIKASH KUMAR(EMPID:152638)
	 */

}
