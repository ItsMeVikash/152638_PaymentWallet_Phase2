package com.cg_152638.pwp2.repo;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cg_152638.pwp2.beans.Customer;
import com.cg_152638.pwp2.util.DataBaseUtil;

public class PaymentWalletRepoImpl implements IPaymentRepo {

	private static Connection connection = null;
	private PreparedStatement preparedStatement = null;
	static {
		try {
			connection = new DataBaseUtil().getConnection();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Customer getCustomerDetails(String mobileNumber) {
		Customer searchedCustomer = null;
		String sql = "select * from parallelproject.customer where mobileNumber = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, mobileNumber);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String mobileno = resultSet.getString("mobilenumber");
				String name = resultSet.getString("name");
				String bal = resultSet.getString("balance");
				String transactions = resultSet.getString("transactions");
				searchedCustomer = new Customer();
				BigDecimal balance = new BigDecimal(bal);
				searchedCustomer.setMobileNumber(mobileno);
				searchedCustomer.setName(name);
				searchedCustomer.setWalletBalance(balance);
				searchedCustomer.setTransaction(new StringBuilder(transactions));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return searchedCustomer;
	}

	@Override
	public boolean addCustomer(Customer newCustomer) {
		boolean result = false;
		String sql = "insert into customer values (?, ?, ?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newCustomer.getMobileNumber());
			preparedStatement.setString(2, newCustomer.getName());
			preparedStatement.setString(3, String.valueOf(newCustomer.getWalletBalance()));
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
			String strDate = sdf.format(date);
			String trans = "Account Created on \t" + strDate + "\nAmount\tType\t\t\tDate\t\tRemaining Balance"
					+ "\n---------------------------------------------------------------------------------------\n";
			preparedStatement.setString(4, trans);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	@Override
	public void depositMoney(Customer customer, BigDecimal depositableAmount) {
		String query = "update customer set balance = ?, transactions = ? where mobilenumber = ?";

		try {
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, String.valueOf(customer.getWalletBalance().add(depositableAmount)));
			preparedStmt.setString(3, customer.getMobileNumber());
			customer.setWalletBalance(customer.getWalletBalance().add(depositableAmount));
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
			String strDate = sdf.format(date);
			String statement = customer.getTransaction().toString() + "\n\u20B9" + depositableAmount + "\tDeposited\t"
					+ strDate + "\t\u20B9" + customer.getWalletBalance();
			customer.setTransaction(new StringBuilder(statement));
			preparedStmt.setString(2, statement);
			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void withdrawMoney(Customer customer, BigDecimal withdrawableAmount) {
		int res = customer.getWalletBalance().subtract(withdrawableAmount).compareTo(new BigDecimal("1000"));
		if (res == 1) {
			String query = "update customer set balance = ?, transactions = ? where mobilenumber = ?";
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, String.valueOf(customer.getWalletBalance().subtract(withdrawableAmount)));
				preparedStmt.setString(3, customer.getMobileNumber());
				customer.setWalletBalance(customer.getWalletBalance().subtract(withdrawableAmount));
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
				String strDate = sdf.format(date);
				String statement = customer.getTransaction().toString() + "\n\u20B9" + withdrawableAmount
						+ "\tWithdrawn\t" + strDate + "\t\u20B9" + customer.getWalletBalance();
				customer.setTransaction(new StringBuilder(statement));
				preparedStmt.setString(2, statement);
				preparedStmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public StringBuilder printTransaction(String mobileNumber) {
		String sql = "select transactions from parallelproject.customer where mobileNumber = ?";
		String transactions = null;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, mobileNumber);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				transactions = resultSet.getString("transactions");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new StringBuilder(transactions);
	}

	@Override
	public void fundTransfer(Customer sendCustomer, Customer recCustomer, BigDecimal transferAmount) {
		int res = sendCustomer.getWalletBalance().subtract(transferAmount).compareTo(new BigDecimal("1000"));
		if (res == 1) {
			String query = "update customer set balance = ?, transactions = ? where mobilenumber = ?";
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, String.valueOf(sendCustomer.getWalletBalance().subtract(transferAmount)));
				preparedStmt.setString(3, sendCustomer.getMobileNumber());
				sendCustomer.setWalletBalance(sendCustomer.getWalletBalance().subtract(transferAmount));
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
				String strDate = sdf.format(date);
				String statement = sendCustomer.getTransaction().toString() + "\n\u20B9" + transferAmount
						+ "\tTransfered To\t" + strDate + "\t\u20B9" + sendCustomer.getWalletBalance();
				preparedStmt.setString(2, statement);
				sendCustomer.setTransaction(new StringBuilder(statement));
				preparedStmt.executeUpdate();

				PreparedStatement preparedStmt1 = connection.prepareStatement(query);
				preparedStmt1.setString(1, String.valueOf(recCustomer.getWalletBalance().add(transferAmount)));
				preparedStmt1.setString(3, recCustomer.getMobileNumber());
				recCustomer.setWalletBalance(recCustomer.getWalletBalance().add(transferAmount));
				String statement2 = recCustomer.getTransaction().toString() + "\n\u20B9" + transferAmount
						+ "\tTransfered from\t" + strDate + "\t\u20B9" + recCustomer.getWalletBalance();
				preparedStmt1.setString(2, statement2);
				recCustomer.setTransaction(new StringBuilder(statement2));
				preparedStmt1.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
