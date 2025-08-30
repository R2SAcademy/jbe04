package project.util;

import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid integer. Please try again.");
            }
        }
    }

    public static Integer readIntAllowNull(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid integer. Please try again.");
            }
        }
    }

    public static Float readFloatAllowNull(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid Float. Please try again.");
            }
        }
    }

    public static String readAllowEmptyString(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return null;
        } else
            return input;
    }

    public static String readNonEmptyString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("⚠️ Input cannot be empty.");
        }
    }
}