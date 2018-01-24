package edu.btp400.w2017.client;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

import com.java.accounts.*;


import edu.btp400.w2017.common.RemoteBank;
/**
 * The financial app brings everything together and allows a user to interact with the bank
 * @author Suhaib
 * @since March 2, 2017
 */
public class RemoteBankClient {

	private static RemoteBank SY;
	/**
	 * The main function, the menu loops until the user inputs 7
	 * all choices call the required function
	 */
	public static void main(String args[]){
		int choice = 0;
		String url = "rmi://localhost:5678/";
		  try {
		System.out.println( "Running a client application..." );

			System.out.println( "+++ retrieving bindings from the RMI Registry\n" +
		                    "  + these services have been registered with the RMI Registry:" );
          
		// use of RMI URL
			String names[] = Naming.list( "rmi://localhost:5678" );

		// retrieve bindings from the RMI Registry
			for(int k=0; k < names.length; k++)
				System.out.println( "....... " + names[k] );

		System.out.println( "\n+++ get remote references\n" +
		                    "  + bind local object variables to remote objects..." );

        //use of RMI URL
		SY = (RemoteBank) Naming.lookup( url + "SenecaYork" );
		while(choice != 6){
			displayMenu();
			choice = menuChoice();
			if(choice == 1){
				open();
			}
			else if(choice == 2){
				close();
			}
			else if(choice == 3){
				depositMoney();
			}
			else if(choice == 4){
				withdrawMoney();
			}
			else if(choice == 5){
				displayAccounts();
			}
		}
		
		System.out.println("\nThanks for visiting the Bank!");
		  }

		   catch( Exception e ) {
					 System.out.println( "Error " + e );}
	}
	
	/**
	 * inital loading of bank with necessary accounts
	 * @param bank - the bank you wish to add accounts to
	 */

	/**
	 * Displays the menu for the user
	 * @param bankName - adds the name of the bank to personalize it
	 */
	static void displayMenu(){
		System.out.println("\nWelcome to the Bank!");
		System.out.println("\n1. Open an account");
		System.out.println("\n2. Close an account");
		System.out.println("\n3. Deposit Money");
		System.out.println("\n4. Withdraw Money");
		System.out.println("\n5. Search Accounts");
		System.out.println("\n6. Exit");
	}
	
	/**
	 * asks for user input and returns it as an int
	 * @return user's input
	 */
	static int menuChoice(){
		System.out.println("\n\n");
		String info = "empty";
		 BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return Integer.parseInt(info);
	}
	
	/**
	 * displays an accounts toString() method
	 * @param account - the account you wish to display
	 */
	static void displayAccount(Account account){
		if(account != null){
			System.out.println(account.toString());
		}
	}	
	
	/**
	 * asks for input to choose an account then asks for account parameters
	 * which it then enters into a constructor to create a new account
	 */
	public static void open(){
		String info = "empty";
		System.out.println("\nPlease enter the account type (SAV/CHQ/GIC):");
		 BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(info.equals("SAV")){
			System.out.println("\nPlease enter SAVINGS account information at one line(e.g. Doe,John;A1234;1000.00;3.65):");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] temp = info.split(";");
			Account _account = new Savings(temp[0].trim(), temp[1].trim(), Double.parseDouble(temp[2].trim()), Double.parseDouble(temp[3].trim()));
			try {
				if(SY.addAccount(_account)){
					System.out.println("\nAccount Creation Successful!");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(info.equals("CHQ")){
			System.out.println("\nPlease enter CHEQUING account information at one line(e.g. Doe,John;A1234;1000.00;2.65,10):");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] temp = info.split(";");
			Account _account = new Chequing(temp[0].trim(), temp[1].trim(), Double.parseDouble(temp[2].trim()), Double.parseDouble(temp[3].trim()),Integer.parseInt(temp[4].trim()));
			try {
				if(SY.addAccount(_account)){
					System.out.println("\nAccount Creation Successful!");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(info.equals("GIC")){
			System.out.println("\nPlease enter CHEQUING account information at one line(e.g. Doe,John;A1234;1000.00;2,10.23):");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] temp = info.split(";");
			Account _account = new GIC(temp[0].trim(), temp[1].trim(), Double.parseDouble(temp[2].trim()), Integer.parseInt(temp[3].trim()),Double.parseDouble(temp[4].trim()));
			try {
				if(SY.addAccount(_account)){
					System.out.println("\nAccount Creation Successful!");
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("\nSorry! You must input SAV for a savings account, CHQ for a chequing account or GIC for a GIC!");
		}
		System.out.println("\nExiting to main menu!");
	}
	
	/**
	 * searches for account based on account number. deletes it.
	 */
	public static void close(){
		String info = "empty";
		System.out.println("\nPlease enter the account number of the account you wish to close:");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		try {
			info = br.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(SY.removeAccount(info) != null){
				System.out.println("\nAccount successfully removed!");
			}
			else{
				System.out.println("\nSorry, unable to find requested account...");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nExiting to main menu!");
	}
	
	/**
	 * searches for chosen account, withdraws amount inputed.
	 */
	public static void withdrawMoney(){
		String info = "empty";
		System.out.println("\nPlease enter the name of the person whose account you wish to withdraw from[lastname, firstname]:");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		try {
			info = br.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(SY.searchByAccountName(info).length != 0){
				Account[] temp = SY.searchByAccountName(info);
				info = "empty";
				System.out.println("\nPlease enter 1 for Chequing and 2 for Savings:");
				br = new BufferedReader( new InputStreamReader(System.in));
				try {
					info = br.readLine().trim();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(info.equals("1")){
					for(int i=0; i < temp.length; i++){
						if(temp[i] instanceof Chequing){
							info = "empty";
							System.out.println("\nPlease input the amount you wish to withdraw:");
							br = new BufferedReader( new InputStreamReader(System.in));
							try {
								info = br.readLine().trim();
							} catch (IOException e) {
								e.printStackTrace();
							}
							if(((Chequing)temp[i]).withdraw(Double.parseDouble(info))){
								System.out.println("\nWithdrawal Successful!");
							}
							else{
								System.out.println("\nWithdrawal Failed!");
							}
						}
					}
				}
				else if(info.equals("2")){
					for(int i=0; i < temp.length; i++){
						if(temp[i] instanceof Savings){
							info = "empty";
							System.out.println("\nPlease input the amount you wish to withdraw:");
							br = new BufferedReader( new InputStreamReader(System.in));
							try {
								info = br.readLine().trim();
							} catch (IOException e) {
								e.printStackTrace();
							}
							if(((Savings)temp[i]).withdraw(Double.parseDouble(info))){
								System.out.println("\nWithdrawal Successful!");
							}
							else{
								System.out.println("\nWithdrawal Failed!");
							}
						}
					}
				}
				else{
					System.out.println("\nYou must Enter 1 or 2!");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nExiting to main menu!");
	}
	
	/**
	 * searches for account. deposits money into it.
	 */
	public static void depositMoney(){
		String info = "empty";
		System.out.println("\nPlease enter the name of the person in whose account you wish to deposit money[lastname,firstname]:");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		try {
			info = br.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(SY.searchByAccountName(info).length != 0){
				Account[] temp = SY.searchByAccountName(info);
				info = "empty";
				System.out.println("\nPlease enter 1 for Chequing and 2 for Savings:");
				br = new BufferedReader( new InputStreamReader(System.in));
				try {
					info = br.readLine().trim();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(info.equals("1")){
					for(int i=0; i < temp.length; i++){
						if(temp[i] instanceof Chequing){
							info = "empty";
							System.out.println("\nPlease input the amount you wish to deposit:");
							br = new BufferedReader( new InputStreamReader(System.in));
							try {
								info = br.readLine().trim();
							} catch (IOException e) {
								e.printStackTrace();
							}
							((Chequing)temp[i]).deposit(Double.parseDouble(info));
							System.out.println("\nDeposit Successful!");
						}
					}
				}
				else if(info.equals("2")){
					for(int i=0; i < temp.length; i++){
						if(temp[i] instanceof Savings){
							info = "empty";
							System.out.println("\nPlease input the amount you wish to deposit:");
							br = new BufferedReader( new InputStreamReader(System.in));
							try {
								info = br.readLine().trim();
							} catch (IOException e) {
								e.printStackTrace();
							}
							((Savings)temp[i]).deposit(Double.parseDouble(info));
							System.out.println("\nDeposit Successful!");
						}
					}
				}
				else{
					System.out.println("\nYou must Enter 1 or 2!");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nExiting to main menu!");
	}
	
	/**
	 * displays accounts
	 * based on what the user chooses it can do 
	 * all accounts
	 * accounts owned by a single person
	 * accounts with a certain balance
	 * @throws RemoteException 
	 */
	public static void displayAccounts() throws RemoteException{
		String info = "empty";
		System.out.println("\nPlease enter 1 for accounts held by a certain person and 2 for all accounts with a certain balance:");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		try {
			info = br.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*if(info.equals("1")){
			System.out.println(SY.getNumberOfAccounts());
			for(int i = 0; i < SY.getNumberOfAccounts(); i++){
				displayAccount(SY.getAllAccounts()[i]);
			}
		}*/
		if(info.equals("1")){
			System.out.println("\nPlease enter the full name[Lastname,FirstName]:");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Account[] temp = SY.searchByAccountName(info);
			for(int i = 0; i < temp.length; i++){
				displayAccount(temp[i]);
			}
		}
		else if(info.equals("2")){
			System.out.println("\nPlease enter the balance:");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Account[] temp = SY.search(Integer.parseInt(info));
			for(int i = 0; i < temp.length; i++){
				displayAccount(temp[i]);
			}
		}
		else{
			System.out.println("\nYou must input 1 or 2!");
		}
		System.out.println("\nExiting to main menu!");
	} 
	
	/**
	 * displays the tax statement 
	 * for all taxable accounts or for a single person's taxable accounts
	 */
	/*public static void displayTaxStatement(){
		String info = "empty";
		System.out.println("\nPlease enter 1 to see all tax statements and 2 to see a single person's tax statement");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		try {
			info = br.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(info.equals("1")){
			Account[] temp = SY.getAllAccounts();
			for(int i = 0; i < temp.length; i++){
				if(temp[i] instanceof Taxable){
					System.out.println(((Taxable) temp[i]).createTaxStatement());
				}
			}
		}
		else if(info.equals("2")){
			System.out.println("\nPlease enter the full name[Lastname,FirstName]:");
			br = new BufferedReader( new InputStreamReader(System.in));
			try {
				info = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Account[] temp = SY.searchByAccountName(info);
			for(int i = 0; i < temp.length; i++){
				if(temp[i] instanceof Taxable){
					System.out.println(((Taxable) temp[i]).createTaxStatement());
				}
			}
		}
		else{
			System.out.println("\nYou must input 1 or 2!");
		}
		System.out.println("\nExiting to main menu");
	}*/
	
}
