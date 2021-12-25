package databaseController;
import java.util.ArrayList;
import database.*;

public class FilterCityQuadrantController {
	/**
	 * 
	 * @param unfilteredHomes this will be responsible for taking in the original list of properties.
	 * @param cityQuadrant this will be responsible to check which quadrant the property is located in the city.
	 * @return newList will return a new list which will contain a new ArrayList containing the properties that match the cityQuadrant.
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, String cityQuadrant){
		ArrayList<Property> newList = new ArrayList<Property>();
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
			
			//matchedListing will be compared to the amount of bedrooms that are available in the properties. 
			if(cityQuadrant.equals( unfilteredHomes.get(i).getCityQuadrant())) {
				newList.add(unfilteredHomes.get(i));
			}
		}
		
		return newList;
	}

}
