package project.util;

public class ValidationUtil {
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidId(int input) {
        return input != 0;
    }

    public static boolean isValidPrice(Float input) {
        if (input == null)
            return true;
        else return input > 0;
    }

    public static boolean isValidYear(Integer input) {
        if (input == null)
            return true;
        else return input >= 1900 && input <= 2100;
    }
}