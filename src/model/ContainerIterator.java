package model;

import java.util.ArrayList;
import java.util.Iterator;

public class ContainerIterator implements Iterator<RS_Container>{

	private ArrayList<RS_Container> containers;
	private int index = 0;
	/** If the previous item was the "next" or the "previous" **/
	private boolean direction = false;
	//LinkedList<String> data;
	
	public ContainerIterator(ArrayList<RS_Container> containers) {
		super();
		this.containers = containers;
		
	}

	@Override
	public boolean hasNext() {
//		if(index < containers.size())
//			return true;
//		return false;
		if(containers != null || containers.isEmpty())
			return true;
		return false;
	}
	
	public boolean hasPrevious() {
//		if(index <= 0 || containers == null) {
//			return false;
//		}
//		return true;
		if(containers != null || containers.isEmpty())
			return true;
		return false;
	}

	@Override
	public RS_Container next() {
		if(direction) {
			direction = false;
			index+=2;
		}
		
		//
		if(index >= containers.size())
			index = containers.size() - index;
		//
		
		RS_Container cnt = containers.get(index);
		//System.out.println(index);
		index++;
		
		return cnt;
	}
	
	public RS_Container previous() {
		if(!direction) {
			direction = true;
			index-=2;
		}
		
//		if(hasPrevious())
//			index--;
		
		if(index <0)
			index = containers.size() - Math.abs(index);
		RS_Container cnt =  containers.get(index);
		//System.out.println(index);
		index--;
		
		return cnt;
	}

}
