package backSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class Prog {

	public static void main(String[] args) {
		// create a new connection to DB
		try (Connection conn = DBconnection.getConnection()) {

			System.out.println(String.format("Connected to database %s successfully.", conn.getCatalog()));
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
