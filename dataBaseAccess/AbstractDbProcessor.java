package dataBaseAccess;

abstract class AbstractDbProcessor implements Query {
	
    protected Object connection;
    protected Object statement;
    
    public abstract void updateDb();
    public abstract void insertIntoDb();
    public abstract void readFromDb();
	

}
