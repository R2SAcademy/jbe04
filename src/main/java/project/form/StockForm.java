package project.form;

import project.entity.Stock;
import project.util.ScannerUtil;

public class StockForm {

    public static int inputStockId(String action) {
        return ScannerUtil.readInt("Enter stock ID to " + action + ": ");

    }

    public static Integer inputStoreId(String action) {
        return ScannerUtil.readInt("Enter store ID to " + action + ": ");
    }

    public static Integer inputProductId(String action) {
        return ScannerUtil.readInt("Enter product ID to " + action + ": ");
    }

    public static Integer inputQuantity(String action) {
        Integer quantity;
        quantity = ScannerUtil.readIntAllowNull("Enter quantity to "+action+": ");
        if (quantity == null)
            return 0;
        return quantity;
    }

    public static Stock inputNewStock() {
        Stock stock = new Stock();

        stock.setStoreId(inputStoreId("create"));
        stock.setProductId(inputProductId("create"));
        stock.setQuantity(inputQuantity("create"));

        return stock;
    }

    public static Stock inputUpdateStock() {
        Stock stock = new Stock();

        stock.setStockId(inputStockId("update"));
        stock.setStoreId(inputStoreId("update"));
        stock.setProductId(inputProductId("update"));
        stock.setQuantity(inputQuantity("update"));

        return stock;
    }

    public static Stock inputSearchStock() {
        Stock stock = new Stock();

        stock.setStoreId(ScannerUtil.readIntAllowNull("Enter store id to search: "));
        stock.setProductId(ScannerUtil.readIntAllowNull("Enter product id to search: "));
        stock.setQuantity(ScannerUtil.readIntAllowNull("Enter low stock threshold: "));

        return stock;
    }

}
