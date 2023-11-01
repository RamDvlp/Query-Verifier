package backSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class Prog {

	public static void main(String[] args) {
		// create a new connection to DB
		try (Connection conn = DBconnection.getConnection()) {

			System.out.println(String.format("Connected to database %s successfully.\n", conn.getCatalog()));
			
			String result = DBconnection.runSelectQuery(conn, "");
			System.out.println("\t" + result + "\n");
			
			//for future use, split the string to parts
			String[] resultData = result.split("\t");
			System.out.println(resultData[0] + " " + resultData[1] + " " + resultData[2]);
			System.out.println(resultData[3] + " " + resultData[4] + " " + resultData[5]);
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
