package model;

import java.util.ArrayList;
import java.util.Iterator;

public class ContainerIterator implements Iterator<RS_Container>{

	private ArrayList<RS_Container> containers;
	private int index = 0;
	private boolean direction = false;
	//LinkedList<String> data;
	
	public ContainerIterator(ArrayList<RS_Container> containers) {
		super();
		this.containers = containers;
		
	}

	@Override
	public boolean hasNext() {
		if(index < containers.size())
			return true;
		return false;
	}
	
	public boolean hasPrevious() {
		if(index <= 0 || containers == null) {
			return false;
		}
		return true;
	}

	@Override
	public RS_Container next() {
		if(direction) {
			direction = false;
			index++;
		}
		
		RS_Container cnt = containers.get(index);
		index++;
		return cnt;
	}
	
	public RS_Container previous() {
		if(!direction) {
			direction = true;
			index--;
		}
		if(hasPrevious())
			index--;
		return containers.get(index);
	}

}
