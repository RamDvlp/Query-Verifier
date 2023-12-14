package model;

import java.util.ArrayList;
import java.util.Iterator;

public class QueryIterator implements Iterator<String>{

	
	private ArrayList<String> queries;
	private int index = 0;
	
	
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

	@Override
	public String next() {
		String query = queries.get(index);
		index++;
		return query;
	}
	
	public String previous() {
		if(index == 0) {
			return queries.get(index);
		} else {
			index--;
		}
		return queries.get(index);
	}
	

}
