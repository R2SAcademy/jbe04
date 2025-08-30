package project.form;

import project.entity.Discount;
import project.util.ScannerUtil;

import java.time.LocalDate;

public class DiscountForm {
    public static Discount inputNewDiscount() {
        String name = ScannerUtil.readNonEmptyString("Enter discount name: ");
        double percentage = ScannerUtil.readInt("Enter discount percentage: ");
        String start = ScannerUtil.readNonEmptyString("Enter start date (yyyy-mm-dd): ");
        String end = ScannerUtil.readNonEmptyString("Enter end date (yyyy-mm-dd): ");
        return new Discount(0, name, percentage, LocalDate.parse(start), LocalDate.parse(end));
    }

    public static Discount inputUpdateDiscount() {
        int id = ScannerUtil.readInt("Enter discount ID to update: ");
        String name = ScannerUtil.readNonEmptyString("Enter discount name: ");
        double percentage = ScannerUtil.readInt("Enter discount percentage: ");
        String start = ScannerUtil.readNonEmptyString("Enter start date (yyyy-mm-dd): ");
        String end = ScannerUtil.readNonEmptyString("Enter end date (yyyy-mm-dd): ");
        return new Discount(id, name, percentage, LocalDate.parse(start), LocalDate.parse(end));
    }

    public static int inputDiscountId(String action) {
        return ScannerUtil.readInt("Enter discount ID to " + action + ": ");
    }
}
