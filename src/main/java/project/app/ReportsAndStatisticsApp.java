package project.app;

import project.dao.ReportsAndStatisticsImpl;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.form.ReportsAndStatisticsForm;
import project.util.Constants;

import java.util.Scanner;

public class ReportsAndStatisticsApp {

    public static void run() {
        ReportsAndStatisticsImpl rasi = new ReportsAndStatisticsImpl();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("=== REPORTS/STATISTICS ===");
            System.out.println("1. Sales by Stores");
            System.out.println("2. Stock Levels by Stores");
            System.out.println("3. Sales by Category");
            System.out.println("4. Best-Selling Products");
            System.out.println("5. Top Customers");
            System.out.println("6. Daily/Monthly Revenue");
            System.out.println("0. Back to main menu");
            System.out.println("Enter your choice: ");
            String inputChoice =  scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case Constants.SALESBYSTORES -> {}
                    case Constants.STOCKLEVELBYSTORE -> {
                        Integer storedId = ReportsAndStatisticsForm.getStoredId();
                        Integer lowStockThreshold = ReportsAndStatisticsForm.getLowStockThreshold();
                        rasi.StockLevelsByStore(storedId, lowStockThreshold);
                    }
                    case Constants.SALESBYCATEGORY -> {
                        Integer categoryId = ReportsAndStatisticsForm.getCategoryId();
                        rasi.SalesByCategory(categoryId);
                    }
                    case Constants.BESTSELLINGPRODUCT -> {
                        System.out.println("=== OPTIONS ===");
                        System.out.println("1. Best-Selling Product By Quantity");
                        System.out.println("2. Best-Selling Product By Revenue");
                        System.out.println("Enter your option: ");
                        String inputOption = scanner.nextLine();

                        int option;
                        try {
                            option = Integer.parseInt(inputOption);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice. Please enter a number.");
                            continue;
                        }

                        int topN = ReportsAndStatisticsForm.getTop_N();

                        switch (option) {
                            case Constants.BESTSELLINGPRODUCTBYQUANTITY -> rasi.BestSellingProductByQuantity(topN);
                            case Constants.BESTSELLINGPRODUCTBYREVENUE ->  rasi.BestSellingProductByRevenue(topN);
                            default -> System.out.println("Invalid option. Try again.");
                        }
                    }
                    case Constants.TOPCUSTOMER -> {
                        int month =  ReportsAndStatisticsForm.getMonth();
                        int year =  ReportsAndStatisticsForm.getYear();

                        rasi.TopCustomer(month, year);
                    }
                    case Constants.DAILYMONTHLYREVENUE -> {
                        int month =  ReportsAndStatisticsForm.getMonth();
                        int year =  ReportsAndStatisticsForm.getYear();

                        rasi.DailyMonthlyRevenue(month, year);
                    }
                    case Constants.BACKTOMAINMENU -> {
                        System.out.println("Goodbye!");
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
