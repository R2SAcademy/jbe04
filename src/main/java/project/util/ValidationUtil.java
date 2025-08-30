package project.util;

public class ValidationUtil {
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isPositiveNumber(int input) {
        if (input > 0) {
            return true;
        }
        return false;
    }
}