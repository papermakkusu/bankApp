package bankApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import consoleApp.ArgParser;
import dataBaseAccess.DbProcessor;

public class BankApplicatlion {
	
	private static Bank bank = new Bank("BigBadBank");
	private static DbProcessor dbProc = new DbProcessor();
	
	public void initialize(ArrayList<Client> clients) {
		for (Client client: clients) {
			BankApplicatlion.bank.addClient(client);
		}
	}
	
	private static final void withdrawOperation(Client client, int accId, Double opAmount) {
		bank.updateInfo();
		client.withdraw(opAmount, accId);
		double accountBalance = client.getBalance(accId);
		dbProc.updateDb(accId, accountBalance);
	}
	
	private static final void depositOperation(Client client, int accId, Double opAmount) {
		bank.updateInfo();
		client.deposit(opAmount, accId);
		double accountBalance = client.getBalance(accId);
		dbProc.updateDb(accId, accountBalance);
	}
	
	public void execCommand(String[] args) throws NumberFormatException, Exception {
		Map<String, String> commands = new HashMap<String, String>();
		commands = ArgParser.parseArgs(args);
		Double opAmount = Double.parseDouble(commands.get("opAmount"));
		bank.updateInfo();
		if (commands.get("operation") == "transfer") {
			if (!bank.checkClientExists(Integer.parseInt(commands.get("sourseAcc")))) {
				throw new Exception (String.format("No such account: %d\n", Integer.parseInt(commands.get("sourseAcc"))));
			} else if (!bank.checkClientExists(Integer.parseInt(commands.get("targetAcc")))) {
				throw new Exception (String.format("No such account: %d\n", Integer.parseInt(commands.get("targetAcc"))));
			} else {
				Client fromClient = BankApplicatlion.bank.getClient(Integer.parseInt(commands.get("sourseAcc")));
				Client toClient = BankApplicatlion.bank.getClient(Integer.parseInt(commands.get("targetAcc")));
				int account1 = Integer.parseInt(commands.get("sourseAcc"));
				int account2 = Integer.parseInt(commands.get("targetAcc"));
				withdrawOperation(fromClient, account1, opAmount);
				depositOperation(toClient, account2, opAmount);
			}
		} else if (commands.get("operation") == "withdrawal") {
			if (!bank.checkClientExists(Integer.parseInt(commands.get("sourseAcc")))) {
				throw new Exception (String.format("No such account: %d\n", Integer.parseInt(commands.get("sourseAcc"))));
			} else {
				Client fromClient = BankApplicatlion.bank.getClient(Integer.parseInt(commands.get("sourseAcc")));
				int account1 = Integer.parseInt(commands.get("sourseAcc"));
				withdrawOperation(fromClient, account1, opAmount);
			}
		} else if (commands.get("operation") == "deposit") {
			if (!bank.checkClientExists(Integer.parseInt(commands.get("targetAcc")))) {
				throw new Exception (String.format("No such account: %d\n", Integer.parseInt(commands.get("targetAcc"))));
			} else {
				Client toClient = BankApplicatlion.bank.getClient(Integer.parseInt(commands.get("targetAcc")));
				int account2 = Integer.parseInt(commands.get("targetAcc"));
				depositOperation(toClient, account2, opAmount);
			}
		}
	}
	
	public void printBankReport() {
		BankApplicatlion.bank.printReport();
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<HashMap> checkDataBase() {
		ArrayList<HashMap> result = dbProc.checkDb();
		return result;
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		
//		Client client1 = new Client("Авдотья Никитишна");
//		client1.addAccount(420332, 101.0);
//		
//		Client client2 = new Client("Василий Васильевич");
//		client2.addAccount(420333, 102.0);
//		
//		Client client3 = new Client("Семён Карпыч");
//		client3.addAccount(420334, 103.0);
		
		BankApplicatlion bankApp = new BankApplicatlion();
//		ArrayList<Client> clients = new ArrayList<Client>(Arrays.asList(client1, client2, client3));
//		bankApp.initialize(clients);

		bankApp.execCommand(args);
	
		System.out.println(bankApp.checkDataBase());
		
	}
}


