package dataBaseAccess;

abstract interface Query {
	
	abstract void updateDb();
	abstract void insertIntoDb();
	abstract void readFromDb();

}
