package project.app;

import project.dao.DiscountDAO;
import project.dao.DiscountDAOImpl;
import project.entity.Discount;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.form.DiscountForm;
import project.util.Constants;

import java.util.List;
import java.util.Scanner;

public class DiscountApp {
    public static void run() {
        DiscountDAO discountDAO = new DiscountDAOImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== DISCOUNT MENU =====");
            System.out.println(Constants.DISCOUNT_VIEW   + ". List all discounts");
            System.out.println(Constants.DISCOUNT_ADD    + ". Add new discount");
            System.out.println(Constants.DISCOUNT_UPDATE + ". Update discount");
            System.out.println(Constants.DISCOUNT_DELETE + ". Delete discount");
            System.out.println(Constants.DISCOUNT_BACK   + ". Back to main menu");
            System.out.print("Choose: ");

            String choiceInput = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case Constants.DISCOUNT_ADD -> {
                        discountDAO.insert(DiscountForm.inputNewDiscount());
                        System.out.println("Discount added successfully.");
                    }
                    case Constants.DISCOUNT_UPDATE -> {
                        discountDAO.update(DiscountForm.inputUpdateDiscount());
                        System.out.println("Discount updated successfully.");
                    }
                    case Constants.DISCOUNT_DELETE -> {
                        discountDAO.delete(DiscountForm.inputDiscountId("delete"));
                        System.out.println("Discount deleted successfully.");
                    }
                    case Constants.DISCOUNT_VIEW -> {
                        List<Discount> discounts = discountDAO.findAll();
                        if (discounts.isEmpty()) {
                            System.out.println("No discounts found.");
                        } else {
                            String header = String.format("%-10s | %-30s | %-10s | %-12s | %-12s",
                                    "ID", "Name", "Percent", "Start", "End");
                            String rowFmt = "%-10d | %-30s | %-10.2f | %-12s | %-12s";
                            System.out.println(header);
                            for (Discount d : discounts) {
                                System.out.printf((rowFmt) + "%n",
                                        d.getDiscountId(),
                                        d.getDiscountName(),
                                        d.getPercentage(),
                                        d.getStartDate(),
                                        d.getEndDate());
                            }
                        }
                    }
                    case Constants.DISCOUNT_BACK -> {
                        System.out.println("Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (DAOException e) {
                GlobalExceptionHandler.handle(e);
            }
        }
    }
}
