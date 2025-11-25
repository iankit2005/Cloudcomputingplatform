package models;

/**
 * NormalUser is a regular user (non-admin).
 */
public class NormalUser extends User {

    public NormalUser() {
        setRole("USER");
    }

    public NormalUser(String name, String email, String password) {
        super(name, email, password, "USER");
    }
}
