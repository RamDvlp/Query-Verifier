package model;

import java.util.Comparator;

public class ComperatorByRow implements Comparator<RS_Container> {

	@Override
	public int compare(RS_Container o1, RS_Container o2) {
		
		return o1.getNumRows() - o2.getNumRows();
	}

}
