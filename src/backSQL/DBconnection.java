package backSQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DBconnection {

    /**
     * Get database connection
     *
     * @return a Connection object
     * @throws SQLException
     */
	
	public static final  String propfileName = "db.properties";
	
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

}