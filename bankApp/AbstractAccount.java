package bankApp;
abstract class AbstractAccount implements Account{
	protected int id;
	protected double balance;
		
	public double getBalance() {
		return this.balance;
	}
	
	//public abstract void deposit(double d);
	
	//public abstract void withdraw(double d);
	
	public int getId() {
		return this.id;
	}
	
	public abstract void printReport();

	public abstract void setBalance(double balance);
	
	}