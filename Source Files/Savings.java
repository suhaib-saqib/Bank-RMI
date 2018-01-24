package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.util.*;
import java.text.*;

/**
 * Savings account is a type of account with a few extra methods inherits from
 * Account
 * 
 * @author Suhaib Saqib
 * @since March 1, 2017
 */
public class Savings extends Account implements Taxable, Serializable {
	private double interestRate;
	private BigDecimal interestIncome, taxDue;
	private NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);

	/**
	 * default constructor invokes super() constructor and sets default interest
	 * rate
	 */
	public Savings() {
		super();
		interestRate = 0.10;
	}

	/**
	 * 4 argument constructor
	 * 
	 * @param nm
	 *            - desired name
	 * @param num
	 *            - desired account number
	 * @param bal
	 *            - initial bank balance
	 * @param iRate
	 *            - interest rate
	 */
	public Savings(String nm, String num, double bal, double iRate) {
		super(nm, num, bal);
		interestRate = iRate;
	}

	/**
	 * overloads Account.getBalance() returns the balance with interest income
	 * included
	 */
	@Override
	public double getBalance() {
		return balance.doubleValue() + balance.doubleValue() * interestRate / 100;
	}

	/**
	 * compares object to current object
	 */
	public boolean equals(Object acc) {
		boolean result = false;
		if (acc instanceof Savings) {
			Savings acc2 = (Savings) acc;
			if (acc2.name.equals(name) && acc2.acc_num.equals(acc_num) && acc2.balance.equals(balance)
					&& acc2.interestRate == interestRate) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * calculates the amount of tax due based on interest income which is all
	 * calculated as a bigdecimal
	 */
	@Override
	public void calculateTax(double taxRate) {
		interestIncome = new BigDecimal(balance.doubleValue() * interestRate / 100);
		BigDecimal rate = new BigDecimal(taxRate / 100);
		if (interestIncome.doubleValue() > 50) {// tax only applies if income is
												// more than $50
			taxDue = interestIncome.multiply(rate);
		} else {
			taxDue = new BigDecimal("0");
		}
	}

	/**
	 * returns the tax value calculated in calculateTax() function if
	 * calculateTax hasn't been called, taxDue will be 0 and so that will be
	 * returned
	 */
	@Override
	public double getTaxAmount() {
		if (taxDue != null) {
			return taxDue.doubleValue();
		} else {
			return 0;
		}
	}

	/**
	 * creates tax statement that neatly formats all tax info for display in a
	 * string
	 */
	@Override
	public String createTaxStatement() {
		this.calculateTax(15);
		String statement = "\nFull Name: "+ getFullName() + "\nAccount Type: SAV" + "\nTax Rate: " + 15 + "%\nAccount Number: " + acc_num + "\nInterest Income: "
				+ n.format(interestIncome.doubleValue()) + "\nAmount of Tax: " + n.format(taxDue.doubleValue());
		return statement;
	}

	/**
	 * neatly formats all savings account information.
	 */
	public String toString() {
		String format = super.toString() + "\nType: SAV\nInterest Rate: " + interestRate
				+ "%\nInterest Income: " + n.format(balance.doubleValue() * interestRate / 100) + "\nFinal Balance: $"
				+ n.format(getBalance());
		return format;
	}

}
