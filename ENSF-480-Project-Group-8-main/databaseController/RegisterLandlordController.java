package databaseController;

import java.sql.*;
import database.Database;

public class RegisterLandlordController {
    private Database database;

    // ***** Constructor *****
    public RegisterLandlordController() {
        database = Database.getInstance();
    }

    // ***** Functions *****
    /*
    Checks if username already exists in the SQL database. If it does, it returns false. If it doesn't,
    adds a new row in the account table with the given username, password, and email, with userType "Registered_Renter"
    */
    public boolean registerLandlord(String username, String password, String email) {
        if (findMatchingLandlord(username)) {
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
                stmt.setString(4, "Landlord");
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
    private boolean findMatchingLandlord(String username) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
