package databaseController;

import java.sql.*;

import database.Database;

public class RegisterPropertyController {
    private Database database;

    // ***** Constructor *****
    public RegisterPropertyController() {
        database = Database.getInstance();
    }

    // ***** Functions *****
    /*
     * Registers a new property by recording it into database using the given
     * parameters.
     */
    public void registerProperty(String type, int bedrooms, int bathrooms, boolean furnished, String cityQuad,
            String ownerUsername, String address) {
        try {
            float feeAmount;
            int feeMonthlyPeriod;
            Statement stmtFee = database.getData().createStatement();
            ResultSet results = stmtFee.executeQuery("SELECT * FROM Fee_Info");
            results.next();
            feeAmount = results.getFloat("Fee_amount");
            feeMonthlyPeriod = results.getInt("Fee_period");

            String query = "INSERT INTO property (PType,Bedrooms,Bathrooms,Furnished,cityQuadrant,Fee,POwner,Fee_Monthly_Period,State,Address) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = database.getData().prepareStatement(query);
            stmt.setString(1, type);
            stmt.setInt(2, bedrooms);
            stmt.setInt(3, bathrooms);
            stmt.setBoolean(4, furnished);
            stmt.setString(5, cityQuad);
            stmt.setFloat(6, feeAmount);
            stmt.setString(7, ownerUsername);
            stmt.setInt(8, feeMonthlyPeriod);
            stmt.setString(9, "Active");
            stmt.setString(10, address);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Unable to register property.");
            e.printStackTrace();
        }
    }

    // ***** Getter *****
    public Database getDatabase() {
        return database;
    }

    // ***** Setter *****
    public void setDatabase(Database database) {
        this.database = database;
    }
}
