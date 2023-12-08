package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeModel {
	
	private File fileCorrectQuery;
	private String selectedModel;
	private ArrayList<String> correctQueries;
	private ArrayList<String> testedQueries;
	private File fileToCheck;
	
	public HomeModel() {
		super();
		correctQueries = new ArrayList<String>();
		testedQueries = new ArrayList<String>();
	}
	
	public File getFileToCheck() {
		return fileToCheck;
	}

	public void setFileToCheck(File fileToCheck) {
		this.fileToCheck = fileToCheck;
	}

	public File getSelectedCorrectFile() {
		return fileCorrectQuery;
	}
	public void setSelectedCorrectFile(File selectedCorrectFile) {
		this.fileCorrectQuery = selectedCorrectFile;
	}
	public String getSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(String selectedModel) {
		this.selectedModel = selectedModel;
		System.out.println(this.selectedModel);
	}
	
	public int uploadQueries(String clickedBTN_Text) {
		int uploaded = 0;
		
		
		
		File fileToUpload = buttonTextToFileFillter(clickedBTN_Text);
		ArrayList<String> tempWrite = new ArrayList<String>();
		if(fileToUpload.equals(this.fileCorrectQuery)) {
			tempWrite = this.correctQueries;
		} if(fileToUpload.equals(this.fileToCheck)) {
			tempWrite = this.testedQueries;
		}
		
		try (Scanner scan = new Scanner(new FileInputStream (fileToUpload))){

            StringBuilder queryBuilder = new StringBuilder();
            boolean inMultiLineComment = false;

            while (scan.hasNextLine()) {
            	//String query = readQuery(scan.nextLine().trim());
            	String line = scan.nextLine().trim();
                //queryBuilder.append(checkQueryLine(line));
                
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
                        tempWrite.add(query);
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
//		for(int i = 0; i<tempWrite.size(); i++) {
//			System.out.println(tempWrite.get(i));
//		}
		System.out.println("\n\n\n");
		for(int i = 0; i<correctQueries.size(); i++) {
			
			System.out.println(correctQueries.get(i));
			
		}
		System.out.println("\n\n\n");
		for(int i = 0; i<testedQueries.size(); i++) {
			
			System.out.println(testedQueries.get(i));
			
		}
		System.out.println("\n\n\n");
		
		
		return uploaded;
		
	}

	private File buttonTextToFileFillter(String clickedBTN_Text) {
		if(clickedBTN_Text.equals("Browse")) {
			return this.fileCorrectQuery;
		}
		if(clickedBTN_Text.equals("Select File")) {
			return this.fileToCheck;
		}
		return null;
	}
	
}
