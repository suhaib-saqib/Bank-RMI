package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * GIC is a taxable class it is a type of account in which the withdraw and
 * deposit functions don't work Inherits from Account and implements Taxable
 * 
 * @author Suhaib Saqib
 * @since March 2, 2017
 */
public class GIC extends Account implements Taxable, Serializable{
	private int investmentPeriod;
	private double interestRate;
	private BigDecimal interestIncome, balanceAtMaturity, taxDue;
	private NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);

	/**
	 * default constructor, puts GIC in safe empty state
	 */
	public GIC() {
		super();
		investmentPeriod = 1;
		interestRate = 1.25;
	}

	/**
	 * 5 argument constructor
	 * 
	 * @param nm
	 *            - name
	 * @param num
	 *            - desired account number
	 * @param bal
	 *            - starting balance
	 * @param iP
	 *            - period of investment
	 * @param iR
	 *            - interest rate
	 */
	public GIC(String nm, String num, double bal, int iP, double iR) {
		super(nm, num, bal);
		investmentPeriod = iP;
		interestRate = iR;
	}

	/**
	 * deposit function does noting if called by a GIC object
	 */
	@Override
	public void deposit(double amount) {
		// empty because you can't deposit into a GIC
	}

	/**
	 * withdraw function does nothing and returns false if called by a GIC
	 * object
	 */
	@Override
	public boolean withdraw(double amount) {
		return false;
		// always false because it can't be withdrawn from until it reaches
		// maturity
	}

	/**
	 * calculates the tax thats due on the investment income
	 */
	@Override
	public void calculateTax(double taxRate) {
		double mature;
		mature = balance.doubleValue() * Math.pow((1 + (interestRate / 100)), investmentPeriod);
		balanceAtMaturity = new BigDecimal(mature);
		interestIncome = balanceAtMaturity.subtract(balance);
		BigDecimal rate = new BigDecimal(taxRate / 100);
		taxDue = interestIncome.multiply(rate);
	}

	/**
	 * returns the tax that's due if its not null, returns 0 if it is
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
	 * orders tax info in a neat and clean way
	 */
	@Override
	public String createTaxStatement() {
		this.calculateTax(15);
		String statement = "\nFull Name: "+ getFullName() + "\nAccount Type: GIC" +"\nTax Rate: " + 15 + "%\nAccount Number: " + acc_num + "\nInterest Income: "
				+ n.format(interestIncome.doubleValue()) + "\nAmount of Tax: " + n.format(taxDue.doubleValue());
		return statement;
	}

	/**
	 * returns the final balance which is the balance at maturity
	 */
	@Override
	public double getBalance() {
		return balance.doubleValue() + getTaxAmount();
	}

	/**
	 * puts all the account information in a neat and easy to read string
	 */
	@Override
	public String toString() {
		this.calculateTax(15);
		String statement = super.toString() + "\nType: GIC\nAnnual Interest Rate: " + interestRate
				+ "%\nPeriod of Investment: " + investmentPeriod + " years\nInterest Income at Maturity: "
				+ n.format(interestIncome.doubleValue()) + "\nBalance at Maturity:  " + n.format(getBalance());
		return statement;
	}

}
