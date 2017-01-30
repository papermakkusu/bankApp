package bankApp;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import consoleApp.ArgParser;

public class Client implements Report{
	
	private static final Logger log = Logger.getLogger(ArgParser.class.getName());
	protected String name;
	private ArrayList<AbstractAccount> accounts = new ArrayList<AbstractAccount>();
	protected AbstractAccount activeAccount;
	protected double initialBalance;
	protected ArrayList<Integer> accountIdList;
	private short numberOfAccounits = 100;
	private int idSecurity = 1000000;
	
	public Client(String name) {
		this.name = name;
		this.accounts = new ArrayList<>(numberOfAccounits);
		this.accountIdList = new ArrayList<>(numberOfAccounits);
		this.initialBalance = 0.0;
	}
	
	public void addAccount(double balance) {
		SavingAccount account = createAccount("SavingAccount");
		account.setBalance(balance);
		this.accounts.add(account);
		this.accountIdList.add(account.getId());
	}
	
	public void addAccount(int accId, double balance) {
		if (!checkIfAccExists(accId)) {
			SavingAccount account = createAccount("SavingAccount", accId);
			account.setBalance(balance);
			this.accounts.add(account);
			this.accountIdList.add(account.getId());
			setActiveAccount(account);
		} else {
			log.log(Level.INFO, String.format("Client %s already has active account #:  %d\n.", this.name, accId));
		}
	}

	public SavingAccount createAccount(String accountType) {
		Random rn = new Random();
		int id = rn.nextInt(idSecurity);
		while (this.accountIdList.contains(id)) {
			id = rn.nextInt(idSecurity);
		}
		SavingAccount account = new SavingAccount(id);
		this.accounts.add(account);
		this.accountIdList.add(id);
		setActiveAccount(account);
		return account;
	}
	
	public boolean checkIfAccExists(int aaccountId) {
		if (this.accountIdList.contains(aaccountId)) {
			return true;
		}
		return false;
		
	}
	
	public SavingAccount createAccount(String accountType, int accId) {
		int id = accId;
		SavingAccount account = new SavingAccount(id);
		this.accounts.add(account);
		this.accountIdList.add(id);
		setActiveAccount(account);
		return account;
	}

	public void dropAccountIdList() {
		
		this.accountIdList = new ArrayList<>(numberOfAccounits);
	}
	
	public AbstractAccount getAccountById(int accountId) {
		for(AbstractAccount account: this.accounts){
			if(account.getId() == accountId) {
				return account;
			}
		}
		return null;
	}
	
	public int getActiveAccountId() {
		int accId = this.activeAccount.getId();
		return accId;
	}
	
	public AbstractAccount getActiveAccount() {
		return this.activeAccount;
	}
	
	public void setActiveAccount(AbstractAccount a) {
		this.activeAccount = a;		
	}
	
	public double getBalance() {
		return this.activeAccount.getBalance();
	}
	
	public double getBalance(int accountId) {
		try {
			for(AbstractAccount account: this.accounts){
				if(account.getId() == accountId) {
					return account.getBalance();
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, String.format("Cannot return balance for account:  %d\n.", accountId), e);
		}
		return 0;
		
	}
	
	public ArrayList<AbstractAccount> getAccounts() {
		if (this.accounts != null) {
			return this.accounts;
		} else {
			return null;
		}
		
	}
	
	public Boolean isAccAvailable(int accId) {
		for(Account account: this.accounts) {
			if (account.getId() == accId) {
				return true;
			}
		}
		return false;
	}
	
	public void deposit(double d) {
		this.activeAccount.deposit(d);
	}
	
	public void deposit(double d, int accId) {
		try { 
			if (getAccountById(accId).getId() == accId) {
				getAccountById(accId).deposit(d);
			}
		} catch (NoSuchElementException e) {
			log.log(Level.SEVERE, String.format("There is no account for client %s with ID: %d\n.", this.name, accId), e);
		}
	}
	
	public void withdraw(double d) {
		this.activeAccount.withdraw(d);
	}
	
	public void withdraw(double d, int accId) {
		try {
			for(Account account: this.accounts) {
				if (account.getId() == accId) {
					account.withdraw(d);
				}
			}
		} catch (NoSuchElementException e) {
			log.log(Level.SEVERE, String.format("There is no account for client %s with ID: %d\n.", this.name, accId), e);
		}
	}
	
	public void printReport() {
		System.out.println("> Name: " + this.name);
		for(AbstractAccount account: this.accounts) {
			account.printReport();
		}
		System.out.println("");
	}
	
	public void printReport(boolean forActiveOnly) {
		System.out.println("Name: " + this.name);
		this.activeAccount.printReport();
	}
	
	public String getName() {
		return this.name;
	}

}
