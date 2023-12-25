package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author micha
 *	Container class for JDBC interface - ResultSet.
 *	ResultSet can not leave the scope in which it created.
 *	In order to decrease coupling, and avoid redundant access to TableView GUI
 *	in DB interface, the container will take the data from a query and pass on to controller
 *	to populate the table.
 */
public class RS_Container {

	private int numRows;
	private int numCols;
	//each row is a map in list;
	private ArrayList<Map<String, Object>> data;
	
	public RS_Container() {
		super();
		this.numRows = 0;
		this.numCols = 0;
		this.data = new ArrayList<Map<String,Object>>();
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public ArrayList<Map<String, Object>> getData() {
		return data;
	}

	public void setData(ArrayList<Map<String, Object>> data) {
		this.data = data;
	}
	
	public void setDataEntery(Map<String,Object> entery) {
		data.add(entery);
	}
	
	
	
	
}
