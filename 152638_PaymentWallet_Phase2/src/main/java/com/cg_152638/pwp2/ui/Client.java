package com.cg_152638.pwp2.ui;

import java.math.BigDecimal;
import java.util.Scanner;
import com.cg_152638.pwp2.beans.Customer;
import com.cg_152638.pwp2.exception.InsufficientBalanceException;
import com.cg_152638.pwp2.exception.InvalidInputException;
import com.cg_152638.pwp2.service.IPaymentService;
import com.cg_152638.pwp2.service.IPaymentServiceValidation;
import com.cg_152638.pwp2.service.PaymentWalletServiceImpl;
import com.cg_152638.pwp2.service.PaymentWalletServiceValidation;

public class Client {

	static IPaymentServiceValidation serviceValidation = new PaymentWalletServiceValidation();
	static IPaymentService service = new PaymentWalletServiceImpl();
	public static void main(String[] args) {
		String choice = null;
		Scanner scanner = new Scanner(System.in);
		do {
			/*
			 * Main Menu which will prompt user to register or login
			 */
			displayMenuMain();
			System.out.println("Enter your Choice: ");
			choice = scanner.next().trim();
			boolean choiceValidation;
			try {
				choiceValidation = serviceValidation.menuChoiceValidation(choice);
				if (choiceValidation) {
					switch (choice) {
					case "1":
						/*
						 * Calling Sign-up method
						 */
						signUp(scanner);
						break;
					case "2":
						/*
						 * Calling Sign-In Method if User already registered
						 */
						signIn(scanner);
						break;
					case "3":
						/*
						 * Exiting The System......Project BY-VIKASH KUMAR(EMPID-152638)
						 */
						exitSystem(scanner);
						break;
					default:
						System.out.println("\nEnter Only Valid Choice (1 to 3)...\n");
						break;
					}
				}
			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
			}
		} while (choice != "3");
	}

	private static void signUp(Scanner scanner) {
		boolean mobileNumberValidation = true;
		boolean nameValidation = true;
		while (mobileNumberValidation) {
			try {
				System.out.println("Enter Mobile Number: ");
				String mobileNumber = scanner.next();
				/*
				 * Validating Mobile Number
				 */
				mobileNumberValidation = serviceValidation.mobileNumberValidation(mobileNumber);
				if (mobileNumberValidation) {
					mobileNumberValidation = false;
					while (nameValidation) {
						try {
							System.out.println("Enter Name: ");
							String name = scanner.next().trim();
							/*
							 * Validating Name...Project by- VIKASH KUMAR(EMPID-152638)
							 */
							nameValidation = serviceValidation.nameValidation(name);
							if (nameValidation) {
								nameValidation = false;
								Customer newCustomer = new Customer();
								newCustomer.setMobileNumber(mobileNumber);
								newCustomer.setName(name);
								/*
								 * Adding Customer To Static Database and printing the Welcome message
								 */
								boolean result = service.addCustomer(newCustomer);
								if (result) {
									System.out.println("\n" + mobileNumber + " is registered Successfully...\n");
								} else {
									System.out.println("\n" + mobileNumber + " is already registered...\n");
								}
							}
						} catch (InvalidInputException invalidInputException) {
							System.out.println(invalidInputException.getMessage());
							nameValidation = true;
						}

					}

				}
			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
				mobileNumberValidation = true;
			}
		}
	}

	private static void signIn(Scanner scanner) {
		boolean mobileNumberValidation = true;
		while (mobileNumberValidation) {
			try {
				System.out.println("Enter Mobile Number: ");
				String mobileNumber = scanner.next();
				mobileNumberValidation = serviceValidation.mobileNumberValidation(mobileNumber);
				if (mobileNumberValidation) {
					mobileNumberValidation = false;
					Customer customer = service.getCustomerDetails(mobileNumber);
					if (customer != null) {
						// once signedIn print Menu
						String choice = null;
						do {
							loginMenu();
							System.out.println("Enter Your Choice...");
							choice = scanner.next().trim();
							boolean choiceValidation;
							try {
								/*
								 * Validating Choice...Project by- VIKASH KUMAR(EMPID-152638)
								 */
								choiceValidation = serviceValidation.menuChoiceValidation(choice);
								if (choiceValidation) {
									switch (choice) {
									case "1":
										/*
										 * Calling Deposit Method
										 */
										deposit(customer, scanner);
										break;
									case "2":
										/*
										 * Calling Withdraw Method
										 */
										withdraw(customer, scanner);
										break;
									case "3":
										/*
										 * Calling For Checking Balance
										 */
										checkBalance(customer, scanner);
										break;
									case "4":
										/*
										 * Fund Transferring
										 */
										fundTransfer(customer, scanner);
										break;
									case "5":
										/*
										 * Printing TransactionList..Project By- VIKASH KUMAR(EMPID: 152638)
										 */
										printTransaction(customer);
										break;
									case "6":
										/*
										 * LoggedIn Customer is getting Logged Out
										 */
										logout();
										break;
									default:
										break;
									}
								}
							} catch (InvalidInputException invalidInputException) {
								System.out.println(invalidInputException.getMessage());
							}

						} while (choice != "6");
					} else {
						System.out.println("\nSorry... No Account found with " + mobileNumber + " Number\n");
					}

				}
			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
				mobileNumberValidation = true;
			}
		}

	}

	private static void deposit(Customer customer, Scanner scanner) {
		boolean amountValidationResult = true;
		while (amountValidationResult) {
			System.out.println("Enter Amount to deposit: ");
			String amount = scanner.next().trim();
			try {
				/*
				 * Amount Validation
				 */
				amountValidationResult = serviceValidation.amountValidation(amount);
				if (amountValidationResult) {
					amountValidationResult = false;
					BigDecimal depositableAmount = new BigDecimal(amount);
					/*
					 * Money Depositing To Current Customer
					 */
					service.depositMoney(customer, depositableAmount);
					System.out.println("\n\u20B9" + depositableAmount + " is deposited successfully"
							+ "\nUpdated Balance is:\u20B9 " + customer.getWalletBalance());
				}
			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
				amountValidationResult = true;
			}
		}

	}

	private static void withdraw(Customer customer, Scanner scanner) {
		boolean amountValidationResult = true;
		while (amountValidationResult) {
			System.out.println("Enter Amount to withdraw: ");
			String amount = scanner.next().trim();
			try {
				/*
				 * Amount Validation
				 */
				amountValidationResult = serviceValidation.amountValidation(amount);
				if (amountValidationResult) {
					amountValidationResult = false;
					BigDecimal withdrawableAmount = new BigDecimal(amount);
					try {
						/*
						 * Withdrawing Money From Customer Account only If Minimum Balance is Maintained
						 */
						service.withdrawMoney(customer, withdrawableAmount);
						System.out.println("\n\u20B9" + withdrawableAmount + " is withdrawn successfully"
								+ "\nUpdated Balance is:\u20B9 " + customer.getWalletBalance());
					} catch (InsufficientBalanceException insufficientBalanceException) {
						System.out.println(insufficientBalanceException.getMessage());
					}

				}
			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
				amountValidationResult = true;
			}
		}

	}

	private static void checkBalance(Customer customer, Scanner scanner) {
		/*
		 * Printing Current Balance. Project By - VIKASH KUMAR(EMPID:152638)
		 */
		System.out.println("\nCurrent Balance \u20B9 " + customer.getWalletBalance());
	}

	private static void fundTransfer(Customer customer, Scanner scanner) {
		boolean mobileNumberValidation = true;
		while (mobileNumberValidation) {
			try {
				System.out.println("Enter Receiver Mobile Number: ");
				String receiverMobileNumber = scanner.next().trim();
				/*
				 * Validating Receiver Mobile Number...Project by- VIKASH KUMAR(EMPID-152638)
				 */
				boolean mobileNumbervalidation = serviceValidation.mobileNumberValidation(receiverMobileNumber);
				if (receiverMobileNumber.equals(customer.getMobileNumber())) {
					System.out.println("\nSender & Receiver Mobile Number Can Not Be Same..\n");
				} else {
					if (mobileNumbervalidation) {
						mobileNumberValidation = false;
						/*
						 * Checking If Receiver Mobile Number Is Registered Or Not
						 */
						Customer receiverCustomer = service.getCustomerDetails(receiverMobileNumber);
						if (receiverCustomer != null) {
							System.out.println("Enter Amount To Transfer: ");
							String amount = scanner.next().trim();
							boolean amountValidationResult = serviceValidation.amountValidation(amount);
							if (amountValidationResult) {
								BigDecimal transferableAmount = new BigDecimal(amount);
								try {
									service.fundTransfer(customer, receiverCustomer, transferableAmount);
									System.out.println("\nFund Transferred Succesfully\n"
											+ "\nUpdated Balance is:\u20B9 " + customer.getWalletBalance());
								} catch (InsufficientBalanceException insufficientBalanceException) {
									System.out.println(insufficientBalanceException.getMessage());
								}
							}
						} else {
							System.out
									.println("\nSorry... No Account found with " + receiverMobileNumber + " Number\n");
						}
					}
				}

			} catch (InvalidInputException invalidInputException) {
				System.out.println(invalidInputException.getMessage());
				mobileNumberValidation = true;
			}
		}

	}

	private static void printTransaction(Customer customer) {
		/*
		 * Printing Transaction List For Logged In Customer
		 */
		StringBuilder transactionSlipList = service.printTransaction(customer.getMobileNumber());
		System.out.println("\n" + transactionSlipList);
	}

	private static void logout() {
		String args[] = { "a" };
		System.out.println("\nLogged out Successfully\n");
		/*
		 * LoggedOut. Project By- VIKASH KUMAR(EMPID- 152638)
		 */
		main(args);
	}

	private static void loginMenu() {
		System.out.println("\n1. Deposit.");
		System.out.println("2. Withdraw.");
		System.out.println("3. Check Balance.");
		System.out.println("4. Fund Transfer.");
		System.out.println("5. Print Transaction.");
		System.out.println("6. Logout");
		System.out.println("------------------------");
	}

	private static void exitSystem(Scanner scanner) {
		System.out.println("Thank you for using Application...!!!!");
		System.exit(0);
		scanner.close();
	}

	private static void displayMenuMain() {
		System.out.println("1. Sign Up");
		System.out.println("2. Sign In");
		System.out.println("3. Exit");
		System.out.println("----------------");

	}

}
