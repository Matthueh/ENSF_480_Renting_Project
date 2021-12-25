package databaseController;
import java.util.ArrayList;
import database.Property;
public class FilterNumberOfBathroomsController {
	
	/**
	 * 
	 * @param unfilteredHomes this will be responsible for taking in the original list of properties.
	 * @param bathrooms this will be responsible the number of bathrooms in the property.
	 * @return newList will return an ArrayList which will contain the amount of filtered bathrooms. 
	 */
	public ArrayList<Property> filter(ArrayList<Property> unfilteredHomes, int bathrooms){
		ArrayList<Property> newList = new ArrayList<Property>();
		
		for(int i = 0; i < unfilteredHomes.size(); i++) {
			
			//matchedListing will be compared to the amount of bathrooms that are available in the properties. 
			if(bathrooms == unfilteredHomes.get(i).getBathrooms()) {
				newList.add(unfilteredHomes.get(i));
			}
		}
		
		return newList;
	}

}
