package project.app;

import project.dao.jdbc.jdbcOrderDAO;
import project.dao.jdbc.jdbcOrderItemDAO;
import project.entity.Order;
import project.entity.OrderItem;
import project.entity.OrderStatus;
import project.exception.DAOException;
import project.service.OrderService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderApp {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bikestores", "root", "2991");
             Scanner sc = new Scanner(System.in)) {

            OrderService service = new OrderService(
                    new jdbcOrderDAO(conn),
                    new jdbcOrderItemDAO(conn)
            );

            while (true) {
                System.out.println("\n===== ORDER MENU =====");
                System.out.println("1. Create new order");
                System.out.println("2. Add item to order");
                System.out.println("3. Update order item");
                System.out.println("4. Remove item from order");
                System.out.println("5. View items of an order");
                System.out.println("6. Find order by ID");
                System.out.println("7. List all orders");
                System.out.println("8. Delete order");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");
                int choice = readInt(sc);

                try {
                    switch (choice) {
                        case 1 -> createOrderFlow(sc, service, conn);
                        case 2 -> addItemFlow(sc, service, conn);
                        case 3 -> updateItemFlow(sc, service, conn);
                        case 4 -> removeItemFlow(sc, service, conn);
                        case 5 -> viewItemsFlow(sc, service, conn);
                        case 6 -> findOrderFlow(sc, service);
                        case 7 -> listOrdersFlow(service);
                        case 8 -> deleteOrderFlow(sc, service);
                        case 0 -> { System.out.println("Exiting. Goodbye!");
                            return; }

                        default -> System.out.println("Invalid choice!");
                    }
                } catch (DAOException e) {
                    System.out.println("Database error: " + e.getMessage());

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Cannot start app: " + e.getMessage());
        }
    }

    // ---------- Flows ----------
    private static void createOrderFlow(Scanner sc, OrderService service, Connection conn) throws DAOException {
        Order order = new Order();
        System.out.print("Customer ID: ");
        order.setCustomerId(readInt(sc));

        // date, status, discount, total
        order.setOrderDate(LocalDate.now());
        order.setStatus(readStatus(sc, "Status [PENDING|CONFIRMED|SHIPPED|CANCELLED] (Enter=default PENDING): "));
        System.out.print("Discount ID (or blank): ");
        String d = sc.nextLine().trim();
        order.setDiscountId(d.isEmpty() ? null : Integer.parseInt(d));

        System.out.print("Total amount: ");
        order.setTotalAmount(readBig(sc));

        List<OrderItem> items = new ArrayList<>();
        System.out.print("Add items now? (YES/NO): ");

        if (sc.nextLine().trim().equalsIgnoreCase("YES")) {

            do {
                OrderItem it = readItem(sc);
                items.add(it);
                System.out.print("Add another item? (YES/NO): ");
            } while (sc.nextLine().trim().equalsIgnoreCase("YES"));
        }

        Order saved = service.createOrder(order, items);
        System.out.println("Order created: " + saved);
    }

    private static void addItemFlow(Scanner sc, OrderService service, Connection conn) throws DAOException {
        System.out.print("Order ID: ");

        int orderId = readInt(sc);
        OrderItem it = readItem(sc);
        service.addItemToOrder(orderId, it);

        System.out.println("Item added.");
    }

    private static void updateItemFlow(Scanner sc, OrderService service, Connection conn) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);

        System.out.print("Product ID: ");
        int productId = readInt(sc);

        System.out.print("New quantity: ");
        int qty = readInt(sc);

        System.out.print("New price: ");
        BigDecimal price = readBig(sc);

        OrderItem it = new OrderItem();
        it.setOrderId(orderId);
        it.setProductId(productId);
        it.setQuantity(qty);
        it.setPrice(price);
        service.updateItem(it);

        System.out.println("Item updated.");
    }

    private static void removeItemFlow(Scanner sc, OrderService service, Connection conn) throws DAOException {
        System.out.print("Order ID: ");

        int orderId = readInt(sc);
        System.out.print("Product ID: ");

        int productId = readInt(sc);
        boolean ok = service.removeItem(orderId, productId);

        System.out.println(ok ? "Item removed." : "Nothing to remove.");
    }

    private static void viewItemsFlow(Scanner sc, OrderService service, Connection conn) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);
        var items = service.listItems(orderId);

        if (items.isEmpty()) {
            System.out.println("No items.");
        } else items.forEach(System.out::println);
    }

    private static void findOrderFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int id = readInt(sc);
        var opt = service.findOrderById(id);

        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            System.out.println("Not found.");
        }
    }

    private static void listOrdersFlow(OrderService service) throws DAOException {
        var orders = service.findAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders.");
        } else orders.forEach(System.out::println);
    }

    private static void deleteOrderFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");

        int id = readInt(sc);
        boolean ok = service.deleteOrder(id);
        System.out.println(ok ? "Order deleted." : "Order not found.");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) { System.out.print("Please enter an integer: "); }
        }
    }

    private static BigDecimal readBig(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) { System.out.print("Please enter a number: "); }
        }
    }

    private static OrderStatus readStatus(Scanner sc, String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim();

        if (s.isEmpty()) {
            return OrderStatus.PENDING;
        }
        try {
            return OrderStatus.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status, default to PENDING.");
            return OrderStatus.PENDING;
        }
    }

    private static OrderItem readItem(Scanner sc) {
        OrderItem it = new OrderItem();
        System.out.print("Product ID: ");
        it.setProductId(readInt(sc));

        System.out.print("Quantity: ");
        it.setQuantity(readInt(sc));

        System.out.print("Price: ");
        it.setPrice(readBig(sc));

        return it;
    }
}
