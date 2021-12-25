package databaseController;

import database.*;
import java.util.ArrayList;

import java.sql.*;

public class AccessAccountController {
    private Database database;

    // ***** Constructor *****
    public AccessAccountController() {
        database = Database.getInstance();
    }

    // ***** Functions *******
    /**
     * Retrieves all the accounts in the database and returns it as an ArrayList.
     */
    public ArrayList<Account> retrieveAccounts() {
        ArrayList<Account> toReturn = new ArrayList<Account>();
        try {
            Statement stmt = database.getData().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM account");
            while (results.next()) {
                String username = results.getString(1);
                String password = results.getString(2);
                String email = results.getString(3);
                String userType = results.getString(4);

                Account toAdd = new Account(username, password, email, userType);
                toReturn.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
