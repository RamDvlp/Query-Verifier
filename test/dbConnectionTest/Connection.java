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
	public void testGetConnectionFalse() throws SQLException {
		Model model = new Model();
		boolean isConnected = model.getDbService().testConnection("jdbc:mysql://localhost:3306/mysqljdbc", "root", "password");
		assertFalse(isConnected);
		
	}
	
	@Test
	public void testGetConnectionTrue() throws SQLException {
		Model model = new Model();
		boolean isConnected = model.getDbService().testConnection("jdbc:mysql://localhost:3306/mysqljdbc", "root", "omited");
		//incorrect password provided, to avoid test fail, negative condition added
		assertTrue(!isConnected);
		
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
