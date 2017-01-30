package bankApp;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataBaseAccess.DbProcessor;

public class Bank implements Report{
	
	protected String name;
	private static final Logger log = Logger.getLogger(Bank.class.getName());
	protected List<Client> clients = new ArrayList<>();
	private static DbProcessor dbProc = new DbProcessor();
	
	@SuppressWarnings("rawtypes")
	private void uploadClient(Client client) {
		try {
			ArrayList<HashMap> result = dbProc.checkDb();
			ArrayList<String> accIds = new ArrayList<String>();
			for (HashMap map: result) {
				accIds.add((String)map.get("accId"));
			}
			if (accIds.contains(String.format("%d", client.getActiveAccount().getId()))) {
				dbProc.updateDb((Integer)client.getActiveAccount().getId(), (Double)client.getActiveAccount().getBalance());
			} else {
				dbProc.insertIntoDb((Integer)client.getActiveAccount().getId(), (String)client.name, (Double)client.getActiveAccount().getBalance());
			}
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, "Cannot upload client info to DB.", e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void downloadClients(){
		try {
			ArrayList<HashMap> result = dbProc.checkDb();
			ArrayList<String> cliNames = new ArrayList<String>();
			for (Client cli: this.clients) {
				cliNames.add(cli.name);
			}
			for (HashMap map: result) {
				ArrayList<AbstractAccount> acts = new ArrayList<AbstractAccount>();
				Client client = getClient(Integer.parseInt((String) map.get("accId")));
				if (client != null) {
					acts = getClient(Integer.parseInt((String) map.get("accId"))).getAccounts();
				}
				if (cliNames.contains(map.get("client"))) {
					ArrayList<Integer> accIds = new ArrayList<Integer>();
					if (acts.size() > 0) {
						for (AbstractAccount acc: acts) {
							accIds.add(acc.getId());
						}
						for (int i = 0; i < acts.size(); i++) {
							SavingAccount acc = (SavingAccount) acts.get(i);
							if (map.get("accId") == String.format("%d", acc.getId())) {
								acc.setBalance((Integer)map.get("balance"));
							} else {
								getClient(Integer.parseInt((String) map.get("accId"))).addAccount(Integer.parseInt((String) map.get("accId")), Double.parseDouble(String.format("%s", map.get("balance")).replace(",", ".")));
							}
						}
					}
				} else {
					Client cli = new Client((String)map.get("client"));
					cli.addAccount((Integer.parseInt((String) map.get("accId"))), Double.parseDouble(String.format("%s", map.get("balance")).replace(",", ".")));
					addClient(cli);
				}
			}
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, "Cannot parse list of clients for download.", e);
		}
	}
	
	public boolean checkClientExists(int accId) throws SQLException {
		downloadClients();
		for (Client client: clients) {
			if (client.getAccountById(accId).getId() == accId) {
				return true;
			}
		}
		return false;
	}
	
	public Bank(String name) {
		this.name = name;
	}
	
	public void addClient(Client client) {
		this.clients.add(client);
		uploadClient(client);
	}
	
	public Client getClient(String clientName) {
		try {
			downloadClients();
			for (Client client: this.clients) {
				if (clientName == client.name) {
				return client;
				} else {
					return null;
				}
			}
		} catch (NoSuchElementException e) {
			log.log(Level.SEVERE, String.format("There is no client with such user name: %s\n.", clientName), e);
		} catch (IllegalArgumentException e) {
			log.log(Level.SEVERE, String.format("There is no client with such user name: %s\n.", clientName), e);
		}
			
		return null;
	}
		
	public Client getClient(int accountNumber){
		
		try {
			for (Client client: this.clients) {
				for (Account account: client.getAccounts()) {
					if (account.getId() == accountNumber) {
						return client;
					} else {
						return null;
					}
				}
			}
		} catch (NoSuchElementException e) {
			log.log(Level.SEVERE, String.format("There is no client with such account number: %d\n.", accountNumber), e);
		} catch (IllegalArgumentException e) {
			log.log(Level.SEVERE, String.format("There is no client with such account number: %d\n.", accountNumber), e);
		}
			
		return null;	
	}
	
	public void updateInfo() {
		downloadClients();
	}
	
	public void printReport() {
		System.out.println("____________________________\nBank: "+ name + "\n");
		for (Client client: this.clients) {
			client.printReport();
		}
		System.out.println("____________________________\n\n");
	}
}