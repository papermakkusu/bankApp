package dataBaseAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbProcessor extends AbstractDbProcessor {

    private DbConnector dbCon = new DbConnector();
    private String name = dbCon.getName();
    private Connection connection;
    private Statement statement;
    
    public String getName() {
    	return this.name;
    }

	public ResultSet readFromDb(int accId) {
		try {
			openConnection();  
			String query = String.format("SELECT * FROM %s WHERE ACCID==%d;", getName(), accId);
			ResultSet resSet = getStatement().executeQuery(query);
			return resSet;
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
        		closeConnection();
			} catch (SQLException e) {
		        e.printStackTrace();
			}
        }
		return null;
	}

	public ResultSet readFromDb(String clientName) {
		try {
			openConnection();  
			String query = String.format("SELECT * FROM %s WHERE CLIENT==%d;", getName(), clientName);
			ResultSet resSet = getStatement().executeQuery(query);
			closeConnection();
			return resSet;
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	
	    	try {
	    		closeConnection();
			} catch (SQLException e) {
		        e.printStackTrace();
			}
        }
		
		return null;
	}
	
	public void updateDb(int accId, double amount){
		try {
			openConnection(); 
			String query = String.format("UPDATE %s SET BALANCE = '%.2f' WHERE ACCID == %d;", getName(), amount, accId);
			getStatement().executeUpdate(query);
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
	    	try {
	    		closeConnection();
			} catch (SQLException e) {
		        e.printStackTrace();
			}
        }
	}
	
	public void insertIntoDb(int accId, String clientName, double amount) {
		try {
			openConnection();
			String query = String.format("INSERT INTO %s (ACCID, CLIENT, BALANCE) VALUES (%d, '%s', '%.2f');", getName(), accId, clientName, amount);
			getStatement().executeUpdate(query);
		} catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	try {
	    		closeConnection();
			} catch (SQLException e) {
		        e.printStackTrace();
			}
	    }
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<HashMap> checkDb() {
		try {
			openConnection();
			String query = String.format("SELECT * FROM %s;", getName());
			ResultSet resSet = getStatement().executeQuery(query);
			ArrayList<HashMap> parsedResult = SqlParser.parseResults(resSet);
			resSet.close();
			closeConnection();
			return parsedResult;
		} catch (SQLException e) {
	        e.printStackTrace();
	    } 
		return null;
	}
	
	public void openConnection() throws SQLException {
		getDbConnector().setConnection();
		setStatement(getDbConnector().c.createStatement());
	}
	
	public void closeConnection() throws SQLException {
		getStatement().close();
		getDbConnector().abortConnection();
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public DbConnector getDbConnector() {
		return this.dbCon;
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public void updateDb() {
		// Abstract method stub
		
	}

	@Override
	public void insertIntoDb() {
		// Abstract method stub
		
	}

	@Override
	public void readFromDb() {
		// Abstract method stub
		
	}
	
}