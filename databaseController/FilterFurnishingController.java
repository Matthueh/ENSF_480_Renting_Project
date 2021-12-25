package databaseController;
import java.util.ArrayList;
import database.*;

public class FilterFurnishingController {
	/**
	 * 
	 * @param unfilteredHomes this will be responsible for taking in the original list of properties.
	 * @param furnished this will be responsible to check if the property is furnished or not.
	 * @return newList will return a new list which will contain a new ArrayList containing the furnished or unfurnished homes as desired.
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, boolean furnished){
		ArrayList<Property> newList = new ArrayList<Property>();
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
			
			//matchedListing will be compared to the amount of bedrooms that are available in the properties. 
			if(furnished == unfilteredHomes.get(i).getFurnished()) {
				newList.add(unfilteredHomes.get(i));
			}
		}
		
		return newList;
	}
}
