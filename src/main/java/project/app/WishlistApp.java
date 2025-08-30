package project.app;

import project.dao.WishlistDAO;
import project.dao.WishlistDAOImpl;
import project.entity.Wishlist;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.form.WishlistForm;
import project.util.Constants;

import java.util.List;
import java.util.Scanner;

public class WishlistApp {
    public static void run() {
        WishlistDAO wishlistDAO = new WishlistDAOImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== WISHLIST MENU =====");
            System.out.println(Constants.WISHLIST_VIEW   + ". List all wishlists");
            System.out.println(Constants.WISHLIST_ADD    + ". Add new wishlist");
            System.out.println(Constants.WISHLIST_DELETE + ". Delete wishlist");
            System.out.println(Constants.WISHLIST_BACK   + ". Back to main menu");
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
                    case Constants.WISHLIST_ADD -> {
                        wishlistDAO.insert(WishlistForm.inputNewWishlist());
                        System.out.println("Wishlist item added successfully.");
                    }
                    case Constants.WISHLIST_DELETE -> {
                        wishlistDAO.delete(WishlistForm.inputWishlistId("delete"));
                        System.out.println("Wishlist item deleted successfully.");
                    }
                    case Constants.WISHLIST_VIEW -> {
                        List<Wishlist> list = wishlistDAO.findAll();
                        if (list.isEmpty()) {
                            System.out.println("No wishlist items found.");
                        } else {
                            String header = String.format("%-12s | %-12s | %-12s",
                                    "Wishlist ID", "Customer ID", "Product ID");
                            String rowFmt = "%-12d | %-12d | %-12d";
                            System.out.println(header);
                            for (Wishlist w : list) {
                                System.out.printf((rowFmt) + "%n",
                                        w.getWishlistId(),
                                        w.getCustomerId(),
                                        w.getProductId());
                            }
                        }
                    }
                    case Constants.WISHLIST_BACK -> {
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
