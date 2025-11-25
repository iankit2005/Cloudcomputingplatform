package models;

/**
 * AdminUser is a specific type of User (Inheritance example).
 */
public class AdminUser extends User {

    public AdminUser() {
        // parent constructor ke through sab set ho sakta hai
        setRole("ADMIN");
    }

    public AdminUser(String name, String email, String password) {
        super(name, email, password, "ADMIN");
    }
}
