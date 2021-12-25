package databaseController;

import java.util.ArrayList;
import database.*;
public class SearchCriteriaController{

	/**
	 *  This Attribute will constantly be updated when it passes through the filters in update method. 
	 */
	private ArrayList<Property> updatedProperties;
	
	//This is what the user enters in.
	private Property matchedListing;
	
	/**
	 * 
	 * @param allProperties 
	 * @param matchedListing
	 */
	public SearchCriteriaController(ArrayList<Property> allProperties, Property matchedListing) {
		
		//getAllProperties() method returns an ArrayList directly from the database.
		//We can then use size which is a function in array list in order to initialize a base size.
		//In the beginning updatedProperties Attribute will be the same size as the property database 
		//because nothing has been filtered. We will then eventually change the size of it accordingly as we 
		//parse through the filters.
		
		updatedProperties = new ArrayList<Property>(allProperties.size());
		
		//We can copy the ArrayList that is provided by the getAllProperties() from the access property class into the updatedProperties.
		for(int i = 0; i < allProperties.size() ; i++) {
			updatedProperties.add(allProperties.get(i));
		}
		//This will set the preferences that the user wants.
		this.matchedListing = matchedListing;
		
	}
	
	
	/**
	 * 
	 * @return will give back the updated properties variable.
	 */
	
	public ArrayList<Property> getUpdatedProperties(){
		return updatedProperties;
	}
	
	
	/**
	 * @param updatedProperties this will call the methods in each of the filters which will then update updatedProperties. 
	 */
	
	public void setUpdatedProperties(ArrayList<Property> updatedProperties) {
		
		//creates new updated properties which will then assign it to the attribute above.
		this.updatedProperties = new ArrayList<Property>(updatedProperties.size());
		
		//This part will copy the updated properties into the attribute above.
		for(int i = 0; i < updatedProperties.size(); i++) {
			this.updatedProperties.add(updatedProperties.get(i));
		}
		
	}
	
	/**
	 * This method will instantiate each of the filter objects and call setUpdatedProperties method multiple times for each of the filters.
	 * 
	 */
	public void update() {
		
		//First we will instantiate all of the classes
		FilterHomeTypeController type = new FilterHomeTypeController();
		FilterNumberOfBathroomsController bathrooms = new FilterNumberOfBathroomsController();
		FilterNumberOfBedroomsController bedrooms = new FilterNumberOfBedroomsController();
		FilterFurnishingController furnishing = new FilterFurnishingController();
		FilterCityQuadrantController cityQuadrant = new FilterCityQuadrantController();
		FilterStateController state = new FilterStateController();
		FilterOwnerController owner = new FilterOwnerController();
		
		//Now update everything!
		setUpdatedProperties(type.filter(this.updatedProperties, matchedListing.getType()));
		setUpdatedProperties(bathrooms.filter(this.updatedProperties, matchedListing.getBathrooms()));
		setUpdatedProperties(bedrooms.filter(this.updatedProperties, matchedListing.getBedrooms()));
		setUpdatedProperties(furnishing.filter(this.updatedProperties, matchedListing.getFurnished()));
		setUpdatedProperties(cityQuadrant.filter(this.updatedProperties, matchedListing.getCityQuadrant()));
		// setUpdatedProperties(state.filter(this.updatedProperties, matchedListing.getState()));
		// setUpdatedProperties(owner.filter(this.updatedProperties, matchedListing.getOwner()));
	}
	
	
}
