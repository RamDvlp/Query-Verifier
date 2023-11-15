package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeModel {
	
	private File selectedCorrectFile;
	private String selectedModel;
	private ArrayList<String> correctQueries;
	
	
	public HomeModel() {
		super();
		correctQueries = new ArrayList<String>();
	}
	
	public File getSelectedCorrectFile() {
		return selectedCorrectFile;
	}
	public void setSelectedCorrectFile(File selectedCorrectFile) {
		this.selectedCorrectFile = selectedCorrectFile;
	}
	public String getSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(String selectedModel) {
		this.selectedModel = selectedModel;
		System.out.println(this.selectedModel);
	}
	
	public int uploadQueries() {
		int uploaded = 0;
		
		try (Scanner scan = new Scanner(new FileInputStream (selectedCorrectFile))){

            StringBuilder queryBuilder = new StringBuilder();
            boolean inMultiLineComment = false;

            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();

                // Ignore use/using
                if (line.toLowerCase().startsWith("us")) {
                	continue;
                }
                
                // Ignore empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Ignore single-line comments
                if (line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }

                // Handle multi-line comments
                if (line.startsWith("/*")) {
                    inMultiLineComment = true;
                }

                if (!inMultiLineComment) {
                    // Extract queries
                    queryBuilder.append(line + " ");

                    // Check for semicolon to denote the end of a query
                    if (line.endsWith(";")) {
                        String query = queryBuilder.toString();
                        correctQueries.add(query);
                        uploaded++;
                        queryBuilder.setLength(0); // Clear the StringBuilder for the next query
                    }
                }

                // Check for the end of multi-line comment
                if (line.endsWith("*/")) {
                    inMultiLineComment = false;
                }
            }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		for(int i = 0; i<correctQueries.size(); i++) {
			System.out.println(correctQueries.get(i));
		}
		return uploaded;
		
	}
	
	
	
	

}
