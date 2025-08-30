// CartException.java
package project.exception;

public class CartException extends Exception {
    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}
