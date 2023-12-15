package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class QueryIterator implements Iterator<String>{

	
	private ArrayList<String> queries;
	private int index = 0;
	private boolean direction = false;
	//LinkedList<String> data;
	
	public QueryIterator(ArrayList<String> queries) {
		super();
		this.queries = queries;
		
	}

	@Override
	public boolean hasNext() {
		if(index < queries.size())
			return true;
		return false;
	}
	
	public boolean hasPrevious() {
		if(index <= 0 || queries == null) {
			return false;
		}
		return true;
	}

	@Override
	public String next() {
		if(direction) {
			direction = false;
			index++;
		}
		
		String query = queries.get(index);
		index++;
		return query;
	}
	
	public String previous() {
		if(!direction) {
			direction = true;
			index--;
		}
		if(hasPrevious())
			index--;
		return queries.get(index);
	}
	

}
