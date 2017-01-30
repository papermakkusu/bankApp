package bankApp;

public class SavingAccount extends AbstractAccount{
	protected int id;
	protected String accountType;
	protected double balance;

	public SavingAccount(int id) {
		this.id = id;
		this.balance = new Double(0.0);
		this.accountType = new String("SavingAccount");
	}
	
	public SavingAccount(int id, double initialBalance) {
		this.id = id;
		this.balance = initialBalance;
		this.accountType = new String("SavingAccount");
	}
	
	@Override
	public void printReport() {
		System.out.println("Account #" + id + "\nBalance: " + this.balance);
	}
	
	@Override
	public void deposit(double d) {
		this.balance += (double)d;
	}
	
	@Override
	public void withdraw(double d) {
		this.balance -= (double)d;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public void setBalance(double balance) {
		this.balance = balance;
		
	}
	
	@Override
	public double getBalance() {
		return this.balance;
	}

}