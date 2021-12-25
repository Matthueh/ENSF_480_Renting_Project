package database;

import java.sql.*;
import javax.swing.*;

public class Database {
    private static Database onlyInstance = new Database();
    private Connection data;

    // ***** Constructor *****
    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            data = DriverManager.getConnection("jdbc:mysql://localhost:3306/propertymanagement", "root", "ryanRoot2001");
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // ***** Getters *****
    public static Database getInstance() {
        return onlyInstance;
    }

    public Connection getData() {
        return data;
    }
}
