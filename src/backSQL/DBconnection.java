package backSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public static enum qType { SELECT, UPDATE, INSERT};

	public static final String propfileName = "db.properties";
	
	private static qType querytype;

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
			//dissectQuery(conn, query);
			ResultSet rs = stmt.executeQuery(query);

			/*
			 * provides information about the columns of the retrieved set, such as column
			 * count, name, data type etc. System.out.println(rs.getMetaData());
			 */

			getColumnsNameOfQuery(rs);

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

	private static void getColumnsNameOfQuery(ResultSet rs) {
		try {
			int columnAmount = rs.getMetaData().getColumnCount();
			columns = new String[columnAmount];
			for (int i = 0; i < columnAmount; i++) {
				columns[i] = rs.getMetaData().getColumnLabel(i + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void dissectQuery(Connection conn, String query) {

//		boolean isSelectQuery = false;
//		ArrayList<String> listOfColumns = new ArrayList<String>();

		queryParts = query.split("[ |\t|\n]+");
		for (String s : queryParts) {
			System.out.println(s);
			if (s.equals("SELECT")) {
				querytype = qType.SELECT;
				// columns = Arrays.copyOfRange(queryParts, 1,
				// Arrays.asList(queryParts).indexOf("FROM"));

				// TODO figure out what to do with asterisk * as well as if it is necessary at
				// all.
			} else if( s.equals("INSERT")) {
				querytype = qType.INSERT;
			} else {
				querytype = qType.UPDATE;
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

	public static int runUpdateQuery(Connection conn, String string) {
		String sqlUpdate = "UPDATE candidates "
                + "SET last_name = ? "
                + "WHERE id = ?";

		String lastName = "Will SEcond";
		int id = 100;

		int lineModified = 0;
		//just like regular statement, but with variables.
		try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)){

			// substitute the question mark ? with the value of variable
			pstmt.setString(1, lastName);
			pstmt.setInt(2, id);
			
			lineModified = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lineModified;
	}

	public static int runInsertQuery(Connection conn, String string) {
		String sql = "INSERT INTO candidates(first_name,last_name,dob,phone,email) "
	            + "VALUES(?,?,?,?,?)";
		
		ResultSet rs = null;
		int newID = 0;

		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			String firstName = "YU";
			// set parameters for statement
			pstmt.setString(1, firstName);
			String lastName = "HAN";
			pstmt.setString(2, lastName);
			Date dob = new Date(11,11,2011);
			pstmt.setDate(3, dob);
			String phone = "11111111";
			pstmt.setString(4, phone);
			String email = "ff@walla.com";
			pstmt.setString(5, email);
			
			int rowReturned = pstmt.executeUpdate();
			if (rowReturned == 1) {
				rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					newID = rs.getInt(1);
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newID;


	}

}