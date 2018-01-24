package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The initial account class from which the other account types inherit. keeps
 * track of account holder's name, account number and balance
 * 
 * @author Suhaib Saqib
 * @since Feb 26, 2017
 */
public class Account implements Serializable {
	protected String fname, lname, name;
	protected String acc_num;
	protected BigDecimal balance;
	private NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
	
	/**
	 * returns the full name of account holder
	 * 
	 * @return the full name
	 */
	public String getFullName() {
		if (name != null) {
			return name;
		} else {
			return null;
		}
	}

	/**
	 * @return first name
	 */
	public String getFirstName() {
		if (fname != null) {
			return fname;
		} else {
			return null;
		}
	}

	/**
	 * @return last name
	 */
	public String getLastName() {
		if (lname != null) {
			return lname;
		} else {
			return null;
		}
	}

	/**
	 * @return account number
	 */
	public String getAccountNumber() {
		return acc_num;
	}

	/**
	 * @return current bank balance
	 */
	public double getBalance() {
		return balance.doubleValue();
	}

	/**
	 * sets the account holder's name
	 * 
	 * @param nm
	 *            - desired name
	 */
	public void setname(String nm) {
		if (nm != null && !nm.isEmpty()) {
			String[] temp = nm.split(", ");
			if (temp[0] != nm) {
				fname = temp[1];
				lname = temp[0];
				name = nm;
			} else {
				fname = "";
				lname = "";
				name = "";
			}
		} else {
			fname = "";
			lname = "";
			name = "";
		}

	}

	/**
	 * setts account number
	 * 
	 * @param num
	 *            - desired account number
	 */
	public void setacc_num(String num) {
		acc_num = num;
	}

	/**
	 * this class was used in previous labs removing it now because deposit
	 * function does this better
	 * 
	 * @param bal
	 *            - desired balance
	 */
	/*
	 * public void setbalance(double bal){ balance = new BigDecimal(bal); }
	 */

	/**
	 * default constructor, sets Account to safe state
	 */
	public Account() {
		fname = "";
		lname = "";
		name = "";
		acc_num = null;
		balance = new BigDecimal("0");
	}

	/**
	 * The withdraw function takes out an amount from the account it first
	 * checks that the withdrawal amount isnt greater than the amount of money
	 * in the account, if it is the function returns false if the amount isn't
	 * greater than the balance, the amount is subtracted from the balance
	 * 
	 * @param amount
	 *            - the amount of money one wishes to withdraw
	 */
	public boolean withdraw(double amount) {
		if (amount > 0) {// prevents a non transaction or negative from
							// happening(i.e. no money added or negative money
							// added
			BigDecimal amt = new BigDecimal(amount);
			if (balance.compareTo(amt) != -1) {
				balance = balance.subtract(amt);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * adds amount to balance only does this if the deposit amount is not
	 * negative
	 * 
	 * @param amount
	 *            - amount one wishes to add to the balance
	 */
	public void deposit(double amount) {
		if (amount >= 0) {// ensures you can't withdraw by adding negative money
			BigDecimal amt = new BigDecimal(amount);
			balance = balance.add(amt);
		}
	}

	/**
	 * 3 argument constructor to initialize an account
	 * 
	 * @param nm
	 *            - desired name (lastname,firstname)
	 * @param num
	 *            - desired account number
	 * @param bal
	 *            - desired initial balance
	 */
	public Account(String nm, String num, double bal) {
		if (nm != null && !nm.isEmpty()) {

			String[] temp = nm.split(", ");
			if (temp[0] != nm) {
				fname = temp[1];
				lname = temp[0];
				name = nm;
			} else {
				fname = "";
				lname = "";
				name = "";
			}
		} else {
			fname = "";
			lname = "";
			name = "";
		}
		acc_num = num;
		if (bal >= 0) {
			balance = new BigDecimal(bal);
		} else {
			balance = new BigDecimal("0");
		}
	}

	/**
	 * prints out Account info in a legible and attractive manner
	 */
	public String toString() {
		String info = "\nFull Name: " + getFullName() + "\nAccount Number: " + acc_num + "\nBalance: " + n.format(balance.doubleValue());
		return info;
	}

	/**
	 * checks to see if an object is equal to current object compares name,
	 * account number and balance
	 */
	public boolean equals(Object acc) {
		boolean result = false;
		if (acc instanceof Account) {
			Account acc2 = (Account) acc;
			if (acc2.name.equals(name) && acc2.acc_num.equals(acc_num) && acc2.balance.equals(balance)) {
				result = true;
			}
		}
		return result;
	}
}
