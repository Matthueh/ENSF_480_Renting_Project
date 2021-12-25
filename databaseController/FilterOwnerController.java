package databaseController;
import java.util.ArrayList;
import database.*;


public class FilterOwnerController {
	
	/**
	 * 
	 * @param unfilteredHomes will be responsible for taking in the unfiltered homes
	 * @param owner will be responsible for the owners username.
	 * @return return a new list consisting of owners with that particular name
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, String owner){

		
		ArrayList<Property> newList = new ArrayList<Property>();
		
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
		
			//matchedListing will be compared to the amount of bedrooms that are available in the properties. 
			if(owner.equals(unfilteredHomes.get(i).getOwner())) {
				newList.add(unfilteredHomes.get(i));
			}
		}
	
		return newList;
	}


}
