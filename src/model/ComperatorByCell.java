package model;

import java.util.Comparator;

public class ComperatorByCell implements Comparator<RS_Container>{

	@Override
	public int compare(RS_Container o1, RS_Container o2) {
		int diff = 0;
		if(o1.getData() == null || o2.getData() == null || o2.getData().isEmpty() || o1.getData().isEmpty())
			return diff+1;
		if((o1.getData().size() != o2.getData().size()) ||
				(o1.getData().get(0).keySet().size() != o2.getData().get(0).keySet().size()))
			return diff+1;
		for(int i = 0; i < o1.getData().size(); i++) {
			for (String key: o1.getData().get(i).keySet()) {
				//diff = o1.getData().get(i).keySet().size()-1;
				if(!o1.getData().get(i).get(key).equals(o2.getData().get(i).get(key))) {
					
					diff--;
				}
			}
			
		}
		return diff;
	}

	
}
