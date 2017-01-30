package dataBaseAccess;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sqlite.SQLiteConnection;

public class DbConnector {

	private static final Logger log = Logger.getLogger(DbConnector.class.getName());
    protected Connection c = null;
    protected Statement stmt = null;
    private String name = "BANK";
    private String dbName = "jdbc:sqlite:C:/Windows/Temp/bankDb.db";
    
    public DbConnector() {
    	initiateDb();
    }
	
	public void initiateDb() {
		try {
			Class.forName("org.sqlite.JDBC");
		    if (!checkDbExists(dbName)) {
		    	setConnection();
		    	log.log(Level.INFO, "Database created successfully");
		    	this.stmt = this.c.createStatement();
			    String sql = String.format("CREATE TABLE %s ", name) +
			                   "(ACCID 		INT 	PRIMARY KEY     NOT NULL," +
			                   " CLIENT     CHAR(50)    			NOT NULL, " + 
			                   " BALANCE    FLOAT     				NOT NULL) "; 
			    this.stmt.executeUpdate(sql);
			    this.stmt.close();
			    abortConnection();
			    log.log(Level.INFO, "Table created successfully");	
		    } else {
		    	log.log(Level.INFO, "Database already exists");	
		    }
		} catch ( Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}		
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDbName() {
		return this.dbName;
	}
	
	private boolean checkDbExists(String dbName) throws SQLException {
		setConnection();
	    DatabaseMetaData dbm = this.c.getMetaData();
	    ResultSet rs = dbm.getTables(null, null, "BANK", null);
	    abortConnection();
	    if (rs.next()) {
	    	rs.close();
	      return true;
	    }
	    rs.close();
	    return false;
	}
	
	public void setConnection() throws SQLException {
		this.c = DriverManager.getConnection(getDbName());
		((SQLiteConnection) this.c).setBusyTimeout(3000);
	}
	
	public void abortConnection() throws SQLException {
		this.c.close();	
	}
}
