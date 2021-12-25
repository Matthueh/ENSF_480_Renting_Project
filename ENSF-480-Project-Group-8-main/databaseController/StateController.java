package databaseController;

import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StateController is a class that represents the state of specific data in the
 * database that can be retrieved or updated by a
 * manager or landlord
 */
public class StateController {
    Database database; // object to access database

    public StateController() {
        database = Database.getInstance();
    } // constructor

    public void setDatabase(Database database) {
        this.database = database;
    } // setter

    public Database getDatabase() {
        return database;
    } // getter

    /**
     * @param period is the monthly period which is specific to the amount of months
     *               that the property is posted on the
     *               rental management system
     *               displayPeriodicalSummary() will display the number of houses
     *               listed within this period, the number of houses
     *               rented within this period, the total number of active listings.
     *               Lastly, a list about rented houses within the period
     *               with the landlord's name, house ID, and house address.
     */
    public String displayPeriodicalSummary(int period) {
        StringBuilder toReturn = new StringBuilder();
        int totalHouses = 0;
        ResultSet result;
        try {
            PreparedStatement state = database.getData().prepareStatement(
                    "SELECT COUNT(*) as allHouses FROM PROPERTY WHERE Fee_Monthly_Period = ?");
            state.setInt(1, period);
            result = state.executeQuery();
            while (result.next()) {
                totalHouses = result.getInt("allHouses");
            }
            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        toReturn.append("The total number of houses listed in " + period + " months is: " + totalHouses + "\n");
        /**
         * ----------------------------------------------------------------------------------------------------------
         **/

        int totalHousesRented = 0;
        ResultSet resultTwo;
        try {
            String PropertyState = "Rented";
            PreparedStatement state = database.getData().prepareStatement(
                    "SELECT COUNT(*) as allHouses FROM PROPERTY WHERE Fee_Monthly_Period = ? AND State = ?");
            state.setInt(1, period);
            state.setString(2, PropertyState);
            resultTwo = state.executeQuery();
            while (resultTwo.next()) {
                totalHousesRented = resultTwo.getInt("allHouses");
            }
            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        toReturn.append("The total number of houses rented in " + period + " months is: " + totalHousesRented + "\n");
        /**
         * ----------------------------------------------------------------------------------------------------------
         **/

        int totalHousesActive = 0;
        ResultSet resultThree;
        try {
            String PropertyState = "Active";
            PreparedStatement state = database.getData()
                    .prepareStatement("SELECT COUNT(*) as allHouses FROM PROPERTY WHERE State = ?");
            state.setString(1, PropertyState);
            resultThree = state.executeQuery();
            while (resultThree.next()) {
                totalHousesActive = resultThree.getInt("allHouses");
            }
            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        toReturn.append("The total number of active houses is: " + totalHousesActive + "\n");
        /**
         * ----------------------------------------------------------------------------------------------------------
         **/

        ResultSet resultFour;
        try {
            String PropertyState = "Rented";
            PreparedStatement state = database.getData().prepareStatement(
                    "SELECT POwner, id, Address FROM PROPERTY WHERE Fee_Monthly_Period = ? AND State = ?");
            state.setInt(1, period);
            state.setString(2, PropertyState);
            resultFour = state.executeQuery();
            toReturn.append(
                    "The following is a list of rented houses with the landlord's name, house's id, and address:" + "\n");
            while (resultFour.next()) {
                String name = resultFour.getString("POwner");
                int id = resultFour.getInt("id");
                String address = resultFour.getString("Address");
                toReturn.append("The landlord's name is " + name + " who owns the house with an id of " + id
                        + " which is located at " + address + "\n");
            }
            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return toReturn.toString();
    }

    /**
     * @param period monthly period of all property registered in the future
     *               updates the monthly period of a property
     */
    public void setFeePeriod(int period) {
        try {
            PreparedStatement state = database.getData().prepareStatement("UPDATE FEE_INFO SET Fee_period = ?");
            state.setInt(1, period);
            int check = state.executeUpdate();
            if (check > 0) {
                System.out.println("Monthly period has been set to " + period);
            } else {
                System.out.println("Error has occurred while setting the fee period");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * @param amount monthly fee amount of future property posted
     *               updates the monthly fee amount of future properties posted
     */
    public void setFeeAmount(int amount) {
        try {
            PreparedStatement state = database.getData().prepareStatement("UPDATE FEE_INFO SET Fee_amount = ?");
            state.setInt(1, amount);
            int check = state.executeUpdate();
            if (check > 0) {
                System.out.println("Monthly fee has been set to " + amount);
            } else {
                System.out.println("Error has occurred while setting the fee amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * getFeeAmount() will retrieve the current fee amount that the Manager has set
     * for future properties that will be posted
     */
    public int getFeeAmount() {
        ResultSet result;
        int amount = 0;
        try {
            PreparedStatement state = database.getData().prepareStatement("SELECT Fee_amount FROM FEE_INFO");
            result = state.executeQuery();
            amount = result.getInt("Fee_amount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
        return amount;
    }

    /**
     * getFeePeriod() will retrieve the current fee period that the Manager has set
     * for future properties that will be posted
     */
    public int getFeePeriod() {
        ResultSet result;
        int period = 0;
        try {
            PreparedStatement state = database.getData().prepareStatement("SELECT Fee_period FROM FEE_INFO");
            result = state.executeQuery();
            period = result.getInt("Fee_period");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
        return period;
    }

    /**
     * @param propertyID is the primary key for a unique property that a Manager can
     *                   access in the database
     */
    public void getProperty(int propertyID) {
        ResultSet result;
        try {
            PreparedStatement state = database.getData().prepareStatement(
                    "SELECT * FROM PROPERTY WHERE id = ?");
            state.setInt(1, propertyID);
            result = state.executeQuery();
            System.out.println("The following is a description of the property in the database:");
            String pType = result.getString("PType");
            int numBedrooms = result.getInt("Bedrooms");
            int numBathrooms = result.getInt("Bathrooms");
            boolean furnished = result.getBoolean("Furnished");
            String quadrant = result.getString("cityQuadrant");
            float fee = result.getFloat("Fee");
            String name = result.getString("POwner");
            int id = result.getInt("id");
            int period = result.getInt("Fee_Monthly_Period");
            String stateProperty = result.getString("State");
            String address = result.getString("Address");
            System.out.println("The following is the retrieved property information:");
            System.out.println("The property type is: " + pType);
            System.out.println("The number of bedrooms is: " + numBedrooms);
            System.out.println("The number of bathrooms is: " + numBathrooms);
            System.out.println("Furnished? " + furnished);
            System.out.println("The city quadrant is: " + quadrant);
            System.out.println("The monthly fee for landlord to pay is : " + fee);
            System.out.println("The landlord's name is: " + name);
            System.out.println("The house id i: " + id);
            System.out.println("The monthly period is: " + id);

            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * @param username is the account username which corresponds to a Landlord,
     *                 Renter, or Manager. (we decided Manager should have an
     *                 account)
     * @param userType is the account type defined as Landlord, Renter, or Manager.
     *                 (we decided Manager should have an account)
     */
    public void getAccountUser(String username, String userType) {
        ResultSet result;
        try {
            PreparedStatement state = database.getData().prepareStatement(
                    "SELECT * FROM ACCOUNT WHERE Username = ? AND UserType = ?");
            state.setString(1, username);
            state.setString(2, userType);
            result = state.executeQuery();
            System.out.println("The following is a information regarding the selected Account user: ");
            String name = result.getString("Username");
            String password = result.getString("APassword");
            String email = result.getString("Email");
            String UserType = result.getString("UserType");
            System.out.println("username is: " + name);
            System.out.println("password is: " + password);
            System.out.println("email is: " + email);
            System.out.println("user type is: " + UserType);

            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * @param state is the state of property that can be changed by a Manager or
     *              Landlord in the database
     * @param id    is the primary key to define a property row
     */
    public void setState(String state, int id) {
        try {
            PreparedStatement stmt = database.getData().prepareStatement("UPDATE PROPERTY SET State = ? WHERE id = ?");
            stmt.setString(1, state);
            stmt.setInt(2, id);
            int check = stmt.executeUpdate();
            if (check > 0) {
                System.out.println(" The state the property with a propertyID of " + id + " has been set to: " + state);
            } else {
                System.out.println("Error has occurred while setting the fee amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
