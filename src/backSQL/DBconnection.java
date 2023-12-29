package backSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ComperatorByColumn;
import model.RS_Container;

public class DBconnection {

	private static String[] columns;

	private static String[] queryParts;
	
	public static enum qType { SELECT, UPDATE, INSERT,DELETE};

	public static final String propfileName = "db.properties";
	
	private static qType querytype;
	
	private Connection conn;
	
	private static DBconnection INSTANCE; 
	
	private DBconnection() {}
	
	/*
	 * Despite the ability of JDBC to connect to several data bases at once,
	 * in order to maintain a single purpose for the application, and not mix different SQL languages,
	 * the model which interfacing with the DB will be singleton - meaning connection to one DB at a time.
	 */
	public static DBconnection getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DBconnection();
		return INSTANCE;
	}

	/**
	 * Get database connection
	 *
	 * @return a Connection object
	 * @throws SQLException
	 */
	public void getConnection() throws SQLException {
		//Connection conn = null;

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
		//return conn;
	}
	
	/**
	 * Made for testing if the connection to Db possiable with the DB detais provided by user.
	 * this is done to reduce the amount of file read\write access e.g, properties.
	 * @param url
	 * @param user
	 * @param password
	 * @return temp connection
	 */
	public Connection getConnectionFromUserInput(String url,String user,String password){
		Connection conn = null;

		// create a connection to the database
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			return null;
			
		}
		return conn;
	}
		
	/**
	 * run a single query and put the result table to table view.
	 * @param query
	 * @param testedContainer 
	 * 
	 */
	public void runAquery(String query, RS_Container testedContainer) {
		dissectQuery(query);

		switch (querytype) {
		case SELECT:
			runSelectQueryForContainer(query, testedContainer);
			break;
		case INSERT:
			//runInsertAndUpdateQueryForContainer(query,testedContainer);
			//break;
		case DELETE:	
		case UPDATE:
			runInsertAndUpdateQueryForContainer(query,testedContainer);
			break;
		default:
			//if all else fails, try run as default - will (should) work for ALTER, INDEX, VIEW, "if not".
			//NOT been tested for the mentioned queries - 30.12; v.01
			runInsertAndUpdateQueryForContainer(query, testedContainer);
			break;
		}
		
	}

	private void runInsertAndUpdateQueryForContainer(String query, RS_Container testedContainer) {
		
		try(Statement stm = conn.createStatement()){
			int numRowsEffected = stm.executeUpdate(query);
			Map<String, Object> mp = new HashMap<String,Object>();
			mp.put("Num' Rows Effected", numRowsEffected);
			testedContainer.getData().add(mp);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * Running the given query (default otherwise) on the data base
	 * 
	 * @param conn
	 * @param query
	 * @return the string representation of the result set data.
	 */
	public String runSelectQuery(String query) {

		StringBuffer resultData = new StringBuffer();

		try (Statement stmt = conn.createStatement()) {

			if (query == null || query == "") {
				query = constructdefaultQuery();
			} else {
				//dissectQuery(query);
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

	public void runSelectQueryForContainer(String query, RS_Container testedContainer) {

		//StringBuffer resultData = new StringBuffer();

		try (Statement stmt = conn.createStatement()) {

			//dissectQuery(conn, query);
			ResultSet rs =  stmt.executeQuery(query);
			
			ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        testedContainer.setNumCols(columnCount);

	        	while (rs.next()) {
	                Map<String, Object> row = new LinkedHashMap<>();
	                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	                    String columnName = metaData.getColumnName(columnIndex);
	                    Object value = rs.getObject(columnIndex);
	                    row.put(columnName, value);
	                }
	                testedContainer.getData().add(row);             
	                
	            }
	            testedContainer.setNumRows(testedContainer.getData().size());
			
			rs.close();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	private void getColumnsNameOfQuery(ResultSet rs) {
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

	private void dissectQuery(String query) {

//		boolean isSelectQuery = false;
//		ArrayList<String> listOfColumns = new ArrayList<String>();

		queryParts = query.split("[ |\t|\n]+");
		for (String s : queryParts) {
			System.out.println(s);
			if (s.toUpperCase().equals("SELECT")) {
				querytype = qType.SELECT;
				
			} 
			if( s.toUpperCase().equals("INSERT")) {
				querytype = qType.INSERT;
				//return runInsertQuery(query);
			} 
			if(s.toUpperCase().equals("UPDATE")){
				querytype = qType.UPDATE;
			}
			if(s.toUpperCase().equals("DELETE")){
				querytype = qType.DELETE;
			}
			return;
		}

	}

	private String constructdefaultQuery() {
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

	public int runUpdateQuery(String string) {
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

	public int runInsertQuery(String string, RS_Container testedContainer) {
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

	public boolean compareQueryByMode(String selectedModel, String correct, String tested) {

		StringBuffer resultDataCorrect = new StringBuffer();
		int res = 0;
		
		try (Statement stmt = conn.createStatement()) {
			try(ResultSet rs1 = stmt.executeQuery(correct); ResultSet rs2 = stmt.executeQuery(tested)){
				switch (selectedModel) {
				case "Column-Wise":
					Comparator<ResultSet> comp = new Comparator<ResultSet>() {
					
						@Override
						public int compare(ResultSet o1, ResultSet o2) {
							int column1=0;
							int column2=0;
							try {
								column1 = o1.getMetaData().getColumnCount();
								column2 = o2.getMetaData().getColumnCount();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return column1- column2;
						}
					};
					//res = comp.compare(rs1, rs2);
					break;

				default:
					break;
				}
	
				
			}
			


		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return res==0;
	}

//	public void fillContainer(String next, RS_Container testedContainer) {
//		// TODO Auto-generated method stub
//		
//	}

}