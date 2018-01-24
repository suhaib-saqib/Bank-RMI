package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Chequing class, non taxable inherits from Account tracks number of
 * transactions and charges per transaction
 * 
 * @author Suhaib Saqib
 * @since March 1, 2017
 */
public class Chequing extends Account implements Serializable{
	private BigDecimal serviceCharge;
	private int maxTransactions;
	private int numTransactions;
	private BigDecimal[] transactions;
	private NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);

	/**
	 * default constructor, calls Account default constructor and initializes
	 * default values
	 */
	public Chequing() {
		super();
		serviceCharge = new BigDecimal("0.25");
		maxTransactions = 3;
		numTransactions = 0;
		transactions = new BigDecimal[maxTransactions];
	}

	/**
	 * 5 argument constructor
	 * 
	 * @param nm
	 *            - desired name
	 * @param num
	 *            - account number
	 * @param bal
	 *            - starting balance
	 * @param serv
	 *            - service charge per transaction
	 * @param maxTran
	 *            - maximum amount of transactions allowed
	 */
	public Chequing(String nm, String num, double bal, double serv, int maxTran) {
		super(nm, num, bal);
		serviceCharge = new BigDecimal(serv);
		maxTransactions = maxTran;
		numTransactions = 0;
		transactions = new BigDecimal[maxTransactions];
	}

	/**
	 * Withdrawal function. similar to the one in Account but keeps track of
	 * transactions and doesn't allow a transaction to occur if there are it
	 * would exceed the max amount
	 */
	@Override
	public boolean withdraw(double amount) {
		if (amount > 0 && numTransactions < maxTransactions) {
			BigDecimal amt = new BigDecimal(amount);
			if (balance.compareTo(amt) != -1) {
				balance = balance.subtract(amt);
				transactions[numTransactions] = new BigDecimal(-amount);
				numTransactions++;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Deposit function. Similar to the one in Account but keeps track of
	 * transactions
	 */
	@Override
	public void deposit(double amount) {
		if (amount >= 0 && numTransactions < maxTransactions) {
			BigDecimal amt = new BigDecimal(amount);
			balance = balance.add(amt);
			transactions[numTransactions] = new BigDecimal(amount);
			numTransactions++;
		}
	}

	/**
	 * checks if this object is equal to another, similar to the Account one
	 * with a few added parameters
	 */
	public boolean equals(Object acc) {
		boolean result = false;
		if (acc instanceof Chequing) {
			Chequing acc2 = (Chequing) acc;
			if (acc2.name.equals(name) && acc2.acc_num.equals(acc_num) && acc2.balance.equals(balance)
					&& acc2.serviceCharge.equals(serviceCharge) && acc2.maxTransactions == maxTransactions
					&& acc2.numTransactions == numTransactions && acc2.transactions.equals(transactions)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * overridden getBalance to give final balance after service charges
	 */
	@Override
	public double getBalance() {
		return balance.doubleValue() - (serviceCharge.doubleValue() * numTransactions);
	}

	/**
	 * formats account information is a clean and neat way
	 */
	public String toString() {
		String format = super.toString() + "\nType: CHQ\nService Charge: " + n.format(serviceCharge.doubleValue())
				+ "\nTotal Service Charge: " + n.format(serviceCharge.doubleValue() * numTransactions)
				+ "\nNumber of Transactions Allowed: " + maxTransactions + "\nList of Transactions:";
		for (int i = 0; i < numTransactions; i++) {
			if (transactions[i].doubleValue() < 0) {
				format = format + " " + transactions[i].doubleValue();
			} else {
				format = format + " +" + n.format(transactions[i].doubleValue());
			}
		}
		format = format + "\nFinal Balance: " + n.format(getBalance());
		return format;
	}
}
