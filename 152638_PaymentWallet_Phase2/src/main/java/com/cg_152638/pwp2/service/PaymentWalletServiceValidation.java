package com.cg_152638.pwp2.service;

import com.cg_152638.pwp2.exception.IPaymentWalletException;
import com.cg_152638.pwp2.exception.InvalidInputException;

/*
 * It Consists of Validation Part such as Mobile Number,
 * Name, Amount & Menu Choice 
 * PROJECT BY- VIKASH KUMAR(EMPID: 152638)
 */
public class PaymentWalletServiceValidation implements IPaymentServiceValidation {

	public boolean menuChoiceValidation(String choice) throws InvalidInputException {
		boolean result = true;
		if (!(choice.length() == 1 && choice.matches("^[0-9]{1}$"))) {
			throw new InvalidInputException(IPaymentWalletException.MESSAGE1);
		}
		return result;
	}

	@Override
	public boolean mobileNumberValidation(String mobileNumber) throws InvalidInputException {
		boolean result = true;
		if (!(mobileNumber.length() == 10 && mobileNumber.matches("^[0-9]+$"))) {
			throw new InvalidInputException(IPaymentWalletException.MESSAGE2);
		}
		return result;
	}

	@Override
	public boolean nameValidation(String name) throws InvalidInputException {
		boolean result = true;
		if (!(name.replaceAll("\\s+", "").matches("^[A-Za-z]*$"))) {
			throw new InvalidInputException(IPaymentWalletException.MESSAGE3);
		}
		return result;
	}

	@Override
	public boolean amountValidation(String amount) throws InvalidInputException {
		boolean result = true;
		if (!(amount.matches("^[0-9]+$"))) {
			throw new InvalidInputException(IPaymentWalletException.MESSAGE4);
		}
		return result;
	}

}
