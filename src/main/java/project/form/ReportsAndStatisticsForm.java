package project.form;

import project.util.ScannerUtil;
import project.util.ValidationUtil;

public class ReportsAndStatisticsForm {
    public static int getYear() {
        while (true) {
            int year = ScannerUtil.readInt("Enter year: ");
            if(ValidationUtil.isValidYear(year)) {
                return year;
            } else {
                System.out.println("Invalid year");
            }
        }
    }

    public static int getMonth() {
        while (true) {
            int month = ScannerUtil.readInt("Enter month: ");
            if(ValidationUtil.isValidMonth(month)) {
                return month;
            } else  {
                System.out.println("Invalid month");
            }
        }
    }

    public static int getTop_N() {
        while (true) {
            int topN = ScannerUtil.readInt("Enter top N(N > 0): ");
            if(ValidationUtil.isValidTopN(topN)) {
                return topN;
            } else  {
                System.out.println("Invalid top N");
            }
        }
    }

    public static Integer getStoredId() {
        while (true) {
            System.out.println("=== Method Reports ===");
            System.out.println("1. Select all storedId");
            System.out.println("2. Select a specific storedId");
            int method = ScannerUtil.readInt("Select method: ");
            if(method == 1) {
                return null;
            } else if(method == 2) {
                int storedId = ScannerUtil.readInt("Select storedId: ");
                if(ValidationUtil.isValidStoredId(storedId)) {
                    return storedId;
                } else  {
                    System.out.println("Invalid storedId");
                }
            } else {
                System.out.println("Invalid method");
            }
        }
    }

    public static Integer getLowStockThreshold() {
        while (true) {
            System.out.println("=== Method Reports ===");
            System.out.println("1. No standard Low Stock Threshold");
            System.out.println("2. Select Low Stock Threshold");
            int method = ScannerUtil.readInt("Select method: ");
            if(method == 1) {
                return null;
            } else if(method == 2) {
                int lowStockThreshold = ScannerUtil.readInt("Select low-stock threshold(>=5): ");
                if(ValidationUtil.isValidLowStockThreshold(lowStockThreshold)) {
                    return lowStockThreshold;
                } else  {
                    System.out.println("Invalid low-stock threshold");
                }
            } else {
                System.out.println("Invalid method");
            }
        }
    }

    public static Integer getCategoryId() {
        while (true) {
            System.out.println("=== Method Reports ===");
            System.out.println("1. Select all categoryId");
            System.out.println("2. Select a specific categoryId");
            int method = ScannerUtil.readInt("Select method: ");
            if(method == 1) {
                return null;
            } else if(method == 2) {
                int categoryId = ScannerUtil.readInt("Select categoryId: ");
                if(ValidationUtil.isValidCategoryId(categoryId)) {
                    return categoryId;
                } else  {
                    System.out.println("Invalid catgoryId");
                }
            } else {
                System.out.println("Invalid method");
            }
        }
    }
}
