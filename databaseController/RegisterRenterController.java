package databaseController;

import java.sql.*;
import database.Database;

public class RegisterRenterController {
    private Database database;
    
    // ***** Constructor *****
    public RegisterRenterController() {
        database = Database.getInstance();
    }

    // ***** Functions *****
    /*
    Checks if username already exists in the SQL database. If it does, it returns false. If it doesn't,
    adds a new row in the account table with the given username, password, and email, with userType "Registered_Renter"
    */
    public boolean registerRenter(String username, String password, String email) {
        if (database == null) {
            System.out.println("Database in rentercontroller is null");
            System.exit(0);
        }
        if (findMatchingRenter(username)) {
            return false;
        } else if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            return false;
        } else {
            try {
                String query = "INSERT INTO account (Username,APassword,Email,UserType) VALUES (?,?,?,?)";
                PreparedStatement stmt = database.getData().prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, "Registered_Renter");
                stmt.executeUpdate();
                stmt.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*
    Checks if the username parameter already exists in the database. It does this by selecting all rows in the
    account table, and checks the username of each one to see if there is a match. If there is a single match, it returns true.
    If it cannot find a match, it returns false.
    */
    private boolean findMatchingRenter(String username) {
        if (database == null) {
            System.out.println("Database in rentercontroller is null");
            System.exit(0);
        }
        ResultSet results;
        try {
            Statement stmt = database.getData().createStatement();
            results = stmt.executeQuery("SELECT * FROM account");
            while (results.next())
            {
                if (results.getString("Username").equalsIgnoreCase(username)) {
                    stmt.close();
                    return true;
                }
            }
            stmt.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param username is the username of renter as they are the only user's who can unsubscribe from the application
     * unsubscribeRenter() will delete a registered renter from the Account table
     */
    public void unsubscribeRenter(String username, String password)
    {
        String renter = "Registered_Renter";
        try
        {
            PreparedStatement stmt = database.getData().prepareStatement("DELETE FROM ACCOUNT WHERE Username = ? AND APassword = ? AND UserType = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, renter);
            int check = stmt.executeUpdate();
            if (check > 0)
            {
                System.out.println("The account with the username, " + username + ", has been unsubscribed.");
            }
            else
            {
                System.out.println("Error: the account with the username, " + username + ", has not been unsubscribed.");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
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