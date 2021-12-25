package userInterface;

import database.Database;
import java.sql.*;

public class LoginController
{
    private Database database;

    // ***** Constructor *****
    public LoginController() {
        database = Database.getInstance();
    }

    // ***** Functions *****
    /*
    Checks the database for a matching username and password in the same row.
    If no such match exists, returns 0.
    If there is a match, returns 1, 2, or 3, if the "UserType" attribute is listed as "Registered_Renter", "Landlord", or "Manager", respectively.
    */
    public int login(String username, String password) {
        String userTypeColumn = "UserType";
        ResultSet results;
        try {
            Statement stmt = database.getData().createStatement();
            results = stmt.executeQuery("SELECT * FROM account");
            while (results.next()) {
                if (results.getString("Username").equalsIgnoreCase(username) &&
                results.getString("APassword").equalsIgnoreCase(password)) {
                    if (results.getString(userTypeColumn).equalsIgnoreCase("Registered_Renter")) {
                        stmt.close();
                        return 1;
                    } else if (results.getString(userTypeColumn).equalsIgnoreCase("Landlord")) {
                        stmt.close();
                        return 2;
                    } else if (results.getString(userTypeColumn).equalsIgnoreCase("Manager")) {
                        stmt.close();
                        return 3;
                    }
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
