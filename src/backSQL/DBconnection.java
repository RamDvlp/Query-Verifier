package backSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class DBconnection {

	
	public static final  String propfileName = "db.properties";
	public static final String defaultQuery = "SELECT first_name, last_name, email FROM candidates";

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
		if(query == null || query == "") {
			query = defaultQuery;
		}
		StringBuffer resultData = new StringBuffer();
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			
			/* provides information about the columns of the retrieved set, such as column count, name, data type etc.
				System.out.println(rs.getMetaData());
			*/
			
			//Retrieving data from the result set
			
			while (rs.next()) {
				resultData.append(rs.getString("first_name") + "\t" + 
				                      rs.getString("last_name")  + "\t" +
				                      rs.getString("email") + "\t");
				                    
				}
			
			rs.close();     
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

    	return resultData.toString();
    	
    }

}