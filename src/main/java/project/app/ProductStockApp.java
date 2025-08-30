package project.app;
import project.dao.ProductDAO;
import project.dao.ProductDAOImpl;
import project.dao.StockDAO;
import project.dao.StockDAOImpl;
import project.entity.Product;
import project.entity.Stock;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.form.ProductForm;
import project.form.StockForm;

import java.util.Scanner;

public class ProductStockApp {
    public static void run() {
        ProductDAO productDAO = new ProductDAOImpl();
        StockDAO stockDAO = new StockDAOImpl();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("\n===== PRODUCT AND STOCK MENU =====");
            System.out.println("1. Add new product");
            System.out.println("2. Update product");
            System.out.println("3. Delete product");
            System.out.println("4. Search product");
            System.out.println("5. Add new stock");
            System.out.println("6. Update stock");
            System.out.println("7. Delete stock");
            System.out.println("8. Search stock");
            System.out.println("0. Back to main menu");
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
                    case 1 -> productDAO.insert(ProductForm.inputNewProduct());
                    case 2 -> productDAO.update(ProductForm.inputUpdateProduct());
                    case 3 -> productDAO.delete(ProductForm.inputProductId("delete"));
                    case 4 ->
                    {
                        for (Product product : productDAO.search(ProductForm.inputNameKeyword(), ProductForm.inputCategoryId(), ProductForm.inputBrandId(), ProductForm.inputModelYear(), ProductForm.inputMinPrice(), ProductForm.inputMaxPrice())) {
                            System.out.println(product);
                        }
                    }

                    case 5 -> stockDAO.insert(StockForm.inputNewStock());
                    case 6 -> stockDAO.update(StockForm.inputUpdateStock());
                    case 7 -> stockDAO.delete(StockForm.inputStockId("delete"));
                    case 8 -> {
                        for (Stock stock : stockDAO.search(StockForm.inputSearchStock())) {
                            System.out.println(stock);
                        }
                    }
                    case 0 -> {
                        System.out.println("Return ...");
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
