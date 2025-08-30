package project.util;

public class ValidationUtil {
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidYear(int year) {
        return year >= 0 && year <= 2025;
    }

    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    public static boolean isValidTopN(int topN) {
        return topN >= 1;
    }

    public static boolean isValidStoredId(int storedId) {
        return storedId >= 1;
    }

    public static boolean isValidLowStockThreshold(int lowStockThreshold) {
        return lowStockThreshold >= 5;
    }

    public static boolean isValidCategoryId(int categoryId) {
        return categoryId >= 1;
    }
}