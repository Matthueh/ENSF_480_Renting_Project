package databaseController;
import java.util.ArrayList;
import database.Property;

public class FilterNumberOfBedroomsController {
	
	/**
	 * 
	 * @param unfilteredHomes this will be responsible for taking in the original list of properties.
	 * @param bedrooms this will be responsible the number of bedrooms in the property.
	 * @return newList will return a new list which will contain a new ArrayList containing the bedrooms desired.
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, int bedrooms){
		ArrayList<Property> newList = new ArrayList<Property>();
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
			
			//matchedListing will be compared to the amount of bedrooms that are available in the properties. 
			if(bedrooms == unfilteredHomes.get(i).getBedrooms()) {
				newList.add(unfilteredHomes.get(i));
			}
		}
		
		return newList;
	}

}
