package databaseController;

import java.util.ArrayList;
import database.*;


public class FilterStateController {
	ArrayList<Property> newList = new ArrayList<Property>();
	
	/**
	 * 
	 * @param unfilteredHomes these are the properties that are going into the filter 
	 * @param state this will handle whether the property is active, or not
	 * @return the filtered list
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, String state){
		for(int i = 0; i < unfilteredHomes.size(); i++) {
		
			//matchedListing will be compared to the amount of bedrooms that are available in the properties. 
			if(state.equals(unfilteredHomes.get(i).getState())) {
				newList.add(unfilteredHomes.get(i));
			}
		}
	
		return newList;
	}

}
