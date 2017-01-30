package bankApp;
abstract interface Account extends Report {
	abstract double getBalance();
	abstract void setBalance(double balance);
	abstract void deposit(double d);
	abstract void withdraw(double d);
	abstract int getId();
}


	