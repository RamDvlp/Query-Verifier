package backSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DBconnection {

	private static String[] columns;

	private static String[] queryParts;

	public static final String propfileName = "db.properties";

	/**
	 * Get database connection
	 *
	 * @return a Connection object
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;

		try (FileInputStream io = new FileInputStream(propfileName)) {

			// load the properties file
			Properties pros = new Properties();
			pros.load(io);

			// assign db parameters
			String url = pros.getProperty("URL");
			String user = pros.getProperty("User");
			String password = pros.getProperty("Password");

			// create a connection to the database
			conn = DriverManager.getConnection(url, user, password);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Running the given query (default otherwise) on the data base
	 * 
	 * @param conn
	 * @param query
	 * @return the string representation of the result set data.
	 */
	public static String runSelectQuery(Connection conn, String query) {

		StringBuffer resultData = new StringBuffer();

		try (Statement stmt = conn.createStatement()) {

			if (query == null || query == "") {
				query = constructdefaultQuery(conn);
			} else {
				dissectQuery(conn, query);
			}
			dissectQuery(conn, query);
			ResultSet rs = stmt.executeQuery(query);

			/*
			 * provides information about the columns of the retrieved set, such as column
			 * count, name, data type etc. System.out.println(rs.getMetaData());
			 */

			int columnAmount = rs.getMetaData().getColumnCount();
			columns = new String[columnAmount];
			for (int i = 0; i < columnAmount; i++) {
				columns[i] = rs.getMetaData().getColumnLabel(i + 1);
			}

			resultData.append("\t\n");

			// Retrieving data from the result set
			while (rs.next()) {
				for (String a : columns) {
					resultData.append(rs.getString(a) + "\t");
				}
				resultData.append("\n");

			}

			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return resultData.toString();

	}

	private static void dissectQuery(Connection conn, String query) {

		boolean isSelectQuery = false;
		ArrayList<String> listOfColumns = new ArrayList<String>();

		queryParts = query.split("[ |\t|\n]+");
		for (String s : queryParts) {
			System.out.println(s);
			if (s.equals("SELECT")) {
				// columns = Arrays.copyOfRange(queryParts, 1,
				// Arrays.asList(queryParts).indexOf("FROM"));

				// TODO figure out what to do with asterisk * as well as if it is necessary at
				// all.
			}
		}

	}

	private static String constructdefaultQuery(Connection conn) {
		String query = "";
		try {
			String catalog = conn.getCatalog(); // The connected DB schema name
			String[] dataType = { "TABLE" };
			ResultSet tableNames = conn.getMetaData().getTables(catalog, null, null, dataType);

			tableNames.next();
			String tableName = tableNames.getString("TABLE_NAME");
			query = "SELECT * FROM " + tableName;

			// DatabaseMetaData metaData = conn.getMetaData();

			// Getting the names of columns of the default table.
			// columns = metaData.getColumns(conn.getCatalog(), null, tableName, null);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return query;
	}

}