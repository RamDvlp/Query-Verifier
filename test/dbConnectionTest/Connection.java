package dbConnectionTest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import backSQL.DBconnection;
import model.Model;

public class Connection {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConnection() throws SQLException {
		Model model = new Model();
		model.getLogInModel().setProperties("jdbc:mysql://localhost:3306/mysqljdbc", "root", "password");
		java.sql.Connection conn = DBconnection.getConnection();
		assertNotNull(conn);
		conn.close();
	}
	
	

//	@Test
//	public void testRunSelectQuery() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRunUpdateQuery() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRunInsertQuery() {
//		fail("Not yet implemented");
//	}

}
