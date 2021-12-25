package databaseController;

import java.util.ArrayList;
import java.sql.*;
import database.*;

//This class has to prompt the user to get a series of Listings and using the attribute matchedListing
//it will then match it 
public class BrowsePropertyController {

	//This attribute will be responsible in getting the what the user is looking for in terms of properties.
	private Property matchedListing;
	
	//This is to instantiate a database that we can then get access to the attribute AccessPropertyController.
	private Database database;
	
	//This attribute will be responsible for receiving the list of filtered properties and returning it to the user.
	private ArrayList<Property> propertiesList; 
	
	
	/**
	 * Constructor to initialize the database.
	 */
	public BrowsePropertyController() {
		//This needs to connect to the database.
		database = Database.getInstance(); 
	}
	
	/**
	 * getter method used to handle matchedListing.
	 * @return matchedListing is the filter that the user wants.
	 */
	public Property getMatchedListing() {
		return matchedListing;
	}
	
	/**
	 * Setter method used to handle matchedListing.
	 * @param houseType is a String of what house type user wants.
	 * @param bedrooms is a integer of how many bedrooms the user wants
	 * @param bathrooms is a int of how many bathrooms the user wants
	 * @param furnished is a boolean of what furnish the user wants
	 * @param cityQuadrant is a String that the user wants
	 * @param owner is a String of who the owner of the property is
	 * @param state is a String indicating whether the house is active or not
	 * @param id is a int indicating the id of the property
	 */
	public void setMatchedListing(String houseType, int bedrooms, int bathrooms, boolean furnished, String cityQuadrant, String owner, String state, int id) {
		//We need to make sure that setProperty(Property property) method is in the Property Class.
		//This sets the matchedListing that will match what the user wants.
		Property matchedListing = new Property(houseType, bedrooms, bathrooms, furnished, cityQuadrant, owner, state, id);
		this.matchedListing = matchedListing;

	}
	
	/**
	 * This method is in charge of obtaining all the properties from the database and storing them in propertiesList.
	 * @return ArrayList<Property> returns a whole list of properties from the database.
	 */
	public ArrayList<Property> getAllProperties(){
		try {
			propertiesList = new ArrayList<Property>();
			//This is the statement to send to SQL.
			String query = "SELECT * FROM property";

			//This is the statement to execute.
			PreparedStatement stmt = database.getData().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String type = rs.getString(1);
				int bedrooms = rs.getInt(2);
				int bathrooms = rs.getInt(3);
				boolean furnished = rs.getBoolean(4);
				String cityQuad = rs.getString(5);
				String owner = rs.getString(7);
				int id = rs.getInt(8);
				String state = rs.getString(10);
				
				//initializes the property
				Property newProp = new Property(type, bedrooms, bathrooms, furnished, cityQuad, owner, state, id);
				//adds it to the list
				propertiesList.add(newProp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return propertiesList;
		
	}
	
	/**
	 * Setter method used to obtain all of the properties in property.
	 */
	public void setFilteredResults() {
		SearchCriteriaController search = new SearchCriteriaController(propertiesList, matchedListing);
		search.update();
		propertiesList = new ArrayList<Property>();
		
		for(int i = 0; i < search.getUpdatedProperties().size(); i++) {
			propertiesList.add(search.getUpdatedProperties().get(i));
		}
			
	}
	
	/**
	 * This method is a getter for filtered properties.
	 * @return filtered properties.
	 */
	public ArrayList<Property> getFilteredProperties(){
		getAllProperties();
		setFilteredResults();
		return propertiesList;
	}
}
