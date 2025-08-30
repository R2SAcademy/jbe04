package project.app;

import project.dao.ReviewsDAO;
import project.dao.ReviewsDAOImpl;
import project.dao.ShipmentDAO;
import project.dao.ShipmentDAOImpl;
import project.entity.Brand;
import project.entity.Reviews;
import project.entity.Shipment;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.form.ReviewsForm;
import project.form.ShipmentForm;
import project.util.Constants;
import project.util.ScannerUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ShipmentReviewApp {
    public static void run() {
        ShipmentDAO shipmentDAO = new ShipmentDAOImpl();
        ReviewsDAO reviewsDAO = new ReviewsDAOImpl();
        Reviews reviews = new Reviews();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== SHIPMENT MENU =====");
            System.out.println("1. Create schedule a shipment");
            System.out.println("2. View shipment information");
            System.out.println("3. List all shipment information");
            System.out.println("4. Update shipment information");
            System.out.println("5. Delete shipment information");
            System.out.println("6. Create product review");
            System.out.println("7. View product's review");
            System.out.println("8. Update product's review");
            System.out.println("9. Delete product's review");
            System.out.println("0. Quit");
            System.out.println("Enter your choice: ");

            String choiceInput = scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            }catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number: ");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> {
                        shipmentDAO.createShipment(ShipmentForm.inputShipment());
                    }
                    case 2 -> {
                        int option ;
                        do {
                            System.out.println("1. View by tracking number");
                            System.out.println("2. View by order id");
                            option = ScannerUtil.readInt("Enter your choice (1 or 2): ");
                            if (option == 1){
                                String input = ScannerUtil.readNonEmptyString("Enter tracking number: ");
                                List<Shipment> shipmentList = shipmentDAO.getShipmentsByTrackingNumber(input);
                                if (shipmentList.isEmpty()) {
                                    System.out.println("No shipment found for tracking number: " + input);
                                } else {
                                    for (Shipment shipment : shipmentList) {
                                        System.out.println(shipment);
                                    }
                                }
                            } else if (option == 2) {
                                int input = ScannerUtil.readInt("Enter id: ");
                                List<Shipment> shipmentList = shipmentDAO.getShipmentsByOrderId(input);
                                for (Shipment shipment : shipmentList) {
                                    System.out.println(shipment);
                                }
                            } else {
                                System.out.println("Invalid choice, please try again");
                            }
                        }while(option != 1 && option != 2);
                    }
                    case 3 -> {
                        int input = ScannerUtil.readInt("Enter: ");
                         Shipment shipment = shipmentDAO.getAllShipments(input);

                         if (shipment == null) {
                             System.out.println("No shipment found!");
                         } else {
                             System.out.println(shipment);
                         }
                    }
                    case 4 -> {
                        Shipment shipmentToUpdate = ShipmentForm.inputUpdateShipment();
                        shipmentDAO.updateShipment(shipmentToUpdate);
                    }
                    case 5 -> {
                        int input = ScannerUtil.readInt("Shipment id to delete: ");
                        shipmentDAO.deleteShipment(input);
                    }
                    case 6 -> {
                        Reviews review = ReviewsForm.inputReview();
                        boolean ok = reviewsDAO.create(review);
                        System.out.println(ok ? "✅ Created." : "⚠ Already reviewed this product.");
                    }

                    case 7 -> {
                        int productId = ScannerUtil.readInt("Enter product_id to view reviews: ");
                        List<Reviews> list = reviewsDAO.findByProductId(productId);
                        if (list.isEmpty()) {
                            System.out.println("No reviews found for this product.");
                        } else {
                            list.forEach(System.out::println);
                        }
                    }

                    case 8 -> {
                        Reviews reviewToUpdate = ReviewsForm.inputUpdate();
                        boolean updated = reviewsDAO.updateByIdAndCustomer(reviewToUpdate);
                        System.out.println(updated ? "Review updated successfully." : "Review not found or update failed.");
                    }

                    case 9 -> {
                        int option;
                        do {
                            System.out.println("1. Delete by review id");
                            System.out.println("2. Delete by product id and customer id");
                            option = ScannerUtil.readInt("Enter your choice (1 or 2): ");

                            if (option == 1) {
                                int reviewId = ScannerUtil.readInt("Enter review id: ");
                                boolean deleted = reviewsDAO.deleteByReviewId(reviewId);
                                System.out.println(deleted ? "Deleted successfully." : "Review not found.");
                            } else if (option == 2) {
                                int customerId = ScannerUtil.readInt("Enter customer id: ");
                                int productId = ScannerUtil.readInt("Enter product id: ");
                                boolean deleted = reviewsDAO.deleteByCustomerAndProduct(customerId, productId);
                                System.out.println(deleted ? "Deleted successfully." : "Review not found.");
                            } else {
                                System.out.println("Invalid choice, please try again");
                            }
                        } while (option != 1 && option != 2);
                    }
                }
            } catch (DAOException e) {
                GlobalExceptionHandler.handle(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
