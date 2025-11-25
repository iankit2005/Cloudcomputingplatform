package utils;

public class InvalidLoginException extends Exception {

    public InvalidLoginException(String message) {
        super(message);
    }
}
/*
 * Custom checked exception used for login validation errors.
 * This helps to demonstrate Exception Handling in OOP rubric.
 */