package database;

public class Property {
    private String type;
    private int bedrooms;
    private int bathrooms;
    private boolean furnished;
    private String cityQuadrant;
    private String owner;
    private String state;
    private int ID;

    // Constructor
    public Property(String type, int beds, int baths, boolean furn, String quad, String owner, String state, int ID) {
        this.type = type;
        bedrooms = beds;
        bathrooms = baths;
        furnished = furn;
        cityQuadrant = quad;
        this.owner = owner;
        this.state = state;
        this.ID = ID;
    }

    // ****** Getters ******
    public String getType() {
        return type;
    }
    public int getBedrooms() {
        return bedrooms;
    }
    public int getBathrooms() {
        return bathrooms;
    }
    public boolean getFurnished() {
        return furnished;
    }
    public String getCityQuadrant() {
        return cityQuadrant;
    }

    public String getOwner() {
        return owner;
    }
    public int getID() {
        return ID;
    }
    public String getState() {
    	return state;
    }

    // ****** Setters ******
    public void setType(String type) {
        this.type = type;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }
    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }
    public void setCityQuadrant(String cityQuadrant) {
        this.cityQuadrant = cityQuadrant;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setState(String state) {
    	this.state = state;
    }
    public void setID(int iD) {
        ID = iD;
    }
}