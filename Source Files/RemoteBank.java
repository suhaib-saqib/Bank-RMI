package edu.btp400.w2017.common;
import java.rmi.*;

import com.java.accounts.Account;


public interface RemoteBank extends Remote {
	public boolean addAccount(Account newAcc) throws RemoteException;
	
	public Account removeAccount(String accNo) throws RemoteException;
	
	public Account[] search(int bal) throws RemoteException;
	
	public Account[] searchByAccountName(String AccountName) throws RemoteException;
}
