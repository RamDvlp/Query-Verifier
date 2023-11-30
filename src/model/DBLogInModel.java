package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBLogInModel {

	private String url;
	private String user;
	private String password;
	private final String propfileName = "db.properties";
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

	public String[] getInitValuesIfAny() {
		
		String [] initVals = new String[3];
		
		try (FileInputStream io = new FileInputStream(propfileName)) {
			
			Properties pros = new Properties();
			pros.load(io);
			
			initVals[0] = pros.getProperty("URL");
			initVals[1] = pros.getProperty("User");
			initVals[2] = pros.getProperty("Password");
			
			return initVals;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	
}
