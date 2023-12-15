package backSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.control.TableView;

public class DB_Service {

	
	private DBconnection dbConnection;

	
	public DB_Service() {
		super();
		this.dbConnection = DBconnection.getInstance();
	}


	public boolean testConnection(String url, String user, String password) {
		if(dbConnection.getConnectionFromUserInput(url, user, password) == null)
			return false;
		return true;	
	}


	/* At this point it's highly unlikely that the connection will fail (unless db server get shut down intentionally 
	by external application) hence no special consideration for the SQL exception.
	
	*/
	public void establishConnection() {
		try {
			dbConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Connected to DB");
	}


	public void runQuery(String query, TableView<Map<String, Object>> table) {
		 dbConnection.runAquery(query, table);
		
	}
	
	
	
	
	
}
