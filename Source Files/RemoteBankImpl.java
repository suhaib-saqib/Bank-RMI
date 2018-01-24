package edu.btp400.w2017.server;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;
import com.java.accounts.*;

import edu.btp400.w2017.common.RemoteBank;

/**
 * Bank class that stores an array list of accounts
 * @author Suhaib
 * @since Feb 24, 2017
 */
public class RemoteBankImpl extends UnicastRemoteObject implements RemoteBank {
	private ArrayList<Account> _accounts;
	private String _bankName;
	
	/**
	 * default constructor, initializes it to Seneca@york by calling one arg constructor
	 */
	public RemoteBankImpl() throws RemoteException {
		_bankName = "Seneca@York";
		_accounts = new ArrayList<Account>();
	}

	/**
	 * 1 arg constructor, sets bank name to chosen one anc creates empty arraylist
	 * @param bankName - name of bank
	 */
	public RemoteBankImpl(String bankName) throws RemoteException{
		_bankName = bankName;
		_accounts = new ArrayList<Account>();
	}

	/**
	 * returns bankname
	 */
	public String getName() {
		return _bankName;
	}

	/**
	 * returns number of accounts
	 */
	public int getNumberOfAccounts() {
		return _accounts.size();
	}

	/**
	 * checks if a bank object is equal to another
	 */
	public boolean equals(Object o) {
		if (o instanceof RemoteBankImpl) {
			RemoteBankImpl that = (RemoteBankImpl) o;
			if (_bankName.equalsIgnoreCase(that._bankName) && _accounts.size() == that._accounts.size()) {
				for (int i = 0; i < _accounts.size(); i++) {
					if (!_accounts.get(i).equals(that._accounts.get(i))) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * neatly organizes bank info
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < _accounts.size(); i++) {
			if (_accounts.get(i) != null) {
				s = s + (i + 1) + ".\n" + _accounts.get(i).toString() + "\n";
			}
		}
		return "Bank Name: " + _bankName + "\nNumber of accounts  : " + _accounts.size() + "\nAccounts:\n" + s;
	}

	/**
	 * adds an account
	 * @param newAcc - account you wish to add
	 * @return - whether it succeeded or failed
	 */
	public boolean addAccount(Account newAcc) throws RemoteException {
		if (newAcc == null) {
			return false;
		}
		for (int i = 0; i < _accounts.size(); i++) {
			if (_accounts.get(i) != null && _accounts.get(i).equals(newAcc)) {
				return false;
			}
		}
		_accounts.add(newAcc);
		return true;
	}

	/**
	 * removes selected account
	 * @param accNo- account number of account to be removed
	 * @return account that was removed
	 */
	public Account removeAccount(String accNo) throws RemoteException{
		if (accNo != null) {
			for (int i = 0; i < _accounts.size(); i++) {
				if (accNo.equals(_accounts.get(i).getAccountNumber())) {
					Account rmAcc = _accounts.get(i);
					_accounts.remove(i);
					return rmAcc;
				}
			}
		}
		return null;
	}

	/**
	 * searches for account based on balance
	 * @param bal - balance to search for
	 * @return array of found accounts
	 */
	public Account[] search(int bal) throws RemoteException {
		Account[] match = new Account[_accounts.size()];
		int size = 0;
		for (int i = 0; i < _accounts.size(); i++) {
			if (_accounts.get(i) != null && bal == _accounts.get(i).getBalance()) {
				match[size++] = _accounts.get(i);
			}
		}
		if (size == 0) {
			return new Account[0];
		}
		return match;
	}

	/**
	 * similar to search(int bal) but uses the name to search instead
	 * @param AccountName - name of account holder
	 * @return account array of desired user
	 */
	public Account[] searchByAccountName(String AccountName) throws RemoteException{
		Account[] match = new Account[_accounts.size()];
		int size = 0;
		for (int i = 0; i < _accounts.size(); i++) {
			if (_accounts.get(i) != null && AccountName.equals(_accounts.get(i).getFullName())) {
				match[size++] = _accounts.get(i);
			}
		}
		if (size == 0) {
			return new Account[0];
		}
		return match;
	}

	/**
	 * returns all the accounts in the bank
	 */
	public Account[] getAllAccounts() {
		return _accounts.toArray(new Account[0]);
	}
}