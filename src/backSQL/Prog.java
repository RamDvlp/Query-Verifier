package backSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class Prog {

//	public static void main(String[] args) {
//		// create a new connection to DB
//		try (Connection conn = DBconnection.getConnection()) {
//
//			System.out.println(String.format("Connected to database %s successfully.\n", conn.getCatalog()));
//			
//			String queryForUpdate = "SELECT * FROM candidates WHERE id = 100";
//			String resultSelect = DBconnection.runSelectQuery(conn, queryForUpdate);
//			System.out.println("\t" + resultSelect + "\n");
//			
//			System.out.println("\nRun update query (hardcoded variable values)");
//			System.out.println("UPDATE candidates " + 
//					"                SET last_name = ? " + 
//					"                + WHERE id = ?");
//			int resultUpdateNumLines = DBconnection.runUpdateQuery(conn, "");
//			
//			System.out.println("Number of rows affected: " + resultUpdateNumLines);
//			
//			System.out.println("\n Excecuting insert (hardcoced values)");
//			System.out.println("INSERT INTO candidates(first_name,last_name,dob,phone,email)\n VALUES(?,?,?,?,?)");
//			//int idReturned = DBconnection.runInsertQuery(conn, "");
//			//System.out.println("\n The id of new entery is: " + idReturned);
//			System.out.println(DBconnection.runSelectQuery(conn, "SELECT * FROM candidates WHERE id = 135"));
//			
//		} catch (SQLException ex) {
//			System.out.println(ex.getMessage());           
//		}
	
		//TODO adding JavaFx nature
//	}
}
