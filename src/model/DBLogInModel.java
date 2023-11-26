package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

import backSQL.DBconnection;

public class DBLogInModel {

	private String url;
	private String user;
	private String password;
	
	/**
	 * Make sure the properties file exists, and write user given values to it (even though they might be incorrect). 
	 * v.01, 26.11.2023 - the password is being saved as well
	 * @param url
	 * @param user
	 * @param password
	 */
	public void setProperties(String url, String user, String password) {
		setURL(url);
		setUser(user);
		setPassword(password);
		
		String propfileName = "db.properties";
		File f = new File(propfileName);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (FileOutputStream io = new FileOutputStream(propfileName)) {
			
			// load the properties file
			Properties pros = new Properties();
			
			pros.put("URL", this.url);
			pros.put("User", this.user);
			pros.put("Password", this.password);
			
			pros.store(io, "DB log in info");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	//TODO - Comparison, if new values equal to old. if yes, skip file operations.
	private void setPassword(String password2) {
		this.password = password2;
		
	}

	private void setUser(String user2) {
		this.user = user2;
	}

	private void setURL(String url2) {
		this.url = url2;
	}

	
}
