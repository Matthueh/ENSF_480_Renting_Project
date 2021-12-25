package databaseController;
import java.util.ArrayList;
import database.*;

public class FilterHomeTypeController{
	
	/**
	 * 
	 * @param unfilteredHomes this will take in the original list of homes.
	 * @param type this will be what the integer type that we want from the property .
	 * @return newList which is a new list that includes properties of the type that is specified.
	 */
	
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, String type){
		//The type of home will be an integer.
		//we should go through the array list and check if the type number in each one
		//matches the type number in the matched listing browse properties.
		
		ArrayList<Property> newList = new ArrayList<Property>();
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
			//matchedListing will take in the type number that has been provided 
			if(type.equals(unfilteredHomes.get(i).getType())) {
				newList.add(unfilteredHomes.get(i));
			}
		}
		
		return newList;

	}
	
	

}
