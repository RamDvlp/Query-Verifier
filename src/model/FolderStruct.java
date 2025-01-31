package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds the information of the content of a folder
 * if a folder is chosen as a source for multiple SQL files, while each file
 * is located in separate folder in root, and represents a student.
 * 
 * 		 root
 * 		/    \
 *   FolderA  FolderB...
 *   	/		\
 *   SQL		SQL
 *   
 *   The assumption that root folders contains only folders.
 *   Each of the inner folders may contain other files besides SQL, such as readme.txt
 */
public class FolderStruct {
	
	private File root;
	private ArrayList<File> innerFolderPaths;
	private HashMap<File, String> folderQueries;
	
	public FolderStruct() {
		super();
		//this.root = root;
		this.innerFolderPaths = new ArrayList<File>();
		this.folderQueries = new HashMap<File, String>();
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public ArrayList<File> getInnerFolderPaths() {
		return innerFolderPaths;
	}

	public void setInnerFolderPaths(ArrayList<File> innerFolderPaths) {
		this.innerFolderPaths = innerFolderPaths;
	}

	public HashMap<File, String> getFolderQueries() {
		return folderQueries;
	}

	public void setFolderQueries(HashMap<File, String> folderQueries) {
		this.folderQueries = folderQueries;
	}
	
	
	
	

}
