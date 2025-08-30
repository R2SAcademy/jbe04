package project.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public static String readNonEmptyString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("⚠️ Input cannot be empty.");
        }
    }

    public static int readNonNegativeInt(String msg) {
        while (true) {
            System.out.println(msg);
            int input = Integer.parseInt(scanner.nextLine().trim());
            if (ValidationUtil.isPositiveNumber(input)) {
                return input;
            } else {
                System.out.println("Input must be greater than 0, please try again: ");
            }
        }
    }

    public static LocalDate inputNonPastDate(String msg) {
        while (true) {
            System.out.println(msg);
            String input = ScannerUtil.readNonEmptyString("Enter shipped date (yyyy-MM-dd): ");
            try {
                LocalDate date = LocalDate.parse(input);
                if (!date.isBefore(LocalDate.now())) {
                    return date;
                } else {
                    System.out.println("Date cannot be in the past. Please try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }
}