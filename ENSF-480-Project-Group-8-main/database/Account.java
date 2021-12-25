package database;

public class Account {
    private String username;
    private String password;
    private String email;
    private String userType;
    
    // Constructor
    public Account(String u, String p, String e, String t) {
        username = u;
        password = p;
        email = e;
        userType = t;
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getUserType() {
        return userType;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }  
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
