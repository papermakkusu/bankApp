package dataBaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SqlParser {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static ArrayList<HashMap> parseResults(ResultSet rs) throws SQLException {
		ArrayList<HashMap> result = new ArrayList<HashMap>();
		while (rs.next()) {
			HashMap map  = new HashMap();
	        String accId = rs.getString("ACCID");
	        String client = rs.getString("CLIENT");
	        String balance = rs.getString("BALANCE");
	        map.put("accId", accId);
	        map.put("client", client);
	        map.put("balance", balance);
	        result.add(map);
		}
		return result;
	}
	
}
