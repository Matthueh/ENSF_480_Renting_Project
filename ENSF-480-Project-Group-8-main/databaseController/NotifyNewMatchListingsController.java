package databaseController;

import java.sql.*;
import database.*;

public class NotifyNewMatchListingsController {

    // Used to instantiate a database in order for operations.
    private Database database;

    // Used to find a registered renter's preferences
    Statement stmt;
    ResultSet results;
    String query1, query2;

    /**
     * Constructor used to initialize the database.
     * 
     * @param Database is a reference to original database created.
     */
    public NotifyNewMatchListingsController() {
        // Needed to connect to the database.
        database = Database.getInstance();

        // Used to make queries more efficient;
        query1 = "SELECT * FROM account WHERE Username=\"";
        query2 = "\"";
    }

    /**
     * Retrieves the desired type of the registered renter identified by username
     * @param username refers to the register renter in question
     */
    public String retrieveDesiredType(String username) throws SQLException {
        stmt = database.getData().createStatement();
        String query = query1 + username + query2;
        results = stmt.executeQuery(query);
        results.next();
        return results.getString("desiredType");
    }

    /**
     * Retrieves the desired number of bedrooms of the registered renter identified by username
     * @param username refers to the register renter in question
     */
    public int retrieveDesiredBedrooms(String username) throws SQLException {
        stmt = database.getData().createStatement();
        String query = query1 + username + query2;
        results = stmt.executeQuery(query);
        results.next();
        return results.getInt("desiredNumberOfBedrooms");
    }

    /**
     * Retrieves the desired number of bathrooms of the registered renter identified by username
     * @param username refers to the register renter in question
     */
    public int retrieveDesiredBathrooms(String username) throws SQLException {
        stmt = database.getData().createStatement();
        String query = query1 + username + query2;
        results = stmt.executeQuery(query);
        results.next();
        return results.getInt("desiredNumberOfBathrooms");
    }

    /**
     * Retrieves the desired city quadrant of the registered renter identified by username
     * @param username refers to the register renter in question
     */
    public String retrieveDesiredCityQuadrant(String username) throws SQLException {
        stmt = database.getData().createStatement();
        String query = query1 + username + query2;
        results = stmt.executeQuery(query);
        results.next();
        return results.getString("cityQuadrant");
    }

    /**
     * Retrieves the desired furnishing of the registered renter identified by username
     * @param username refers to the register renter in question
     */
    public Boolean retrieveDesiredFurnishing(String username) throws SQLException {
        stmt = database.getData().createStatement();
        String query = query1 + username + query2;
        results = stmt.executeQuery(query);
        results.next();
        return results.getBoolean("desiredFurnishing");
    }

    /**
     * Modify the attributes describing the registered renter's preferences
     * @param username refers to the registered renter
     * @param type refers to desired type
     * @param bedrooms refers to desired number of bedrooms
     * @param bathrooms refers to desired number of bathrooms
     * @param cityQuad refers to desired city quadrant
     * @param furnishing refers to desired furnishing
     * @throws SQLException
     */
    public void modifyNotifications(String username, String type, int bedrooms, int bathrooms, String cityQuad, boolean furnishing) throws SQLException {
        try {
            String query = "UPDATE account SET desiredType=?,desiredNumberOfBedrooms=?,desiredNumberofBathrooms=?,cityQuadrant=?,desiredFurnishing=? WHERE Username=\"" + username + "\"";
            PreparedStatement stmt2 = database.getData().prepareStatement(query);
            stmt2.setString(1, type);
            stmt2.setInt(2, bedrooms);
            stmt2.setInt(3, bathrooms);
            stmt2.setString(4, cityQuad);
            stmt2.setBoolean(5, furnishing);
            stmt2.executeUpdate();
            stmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
