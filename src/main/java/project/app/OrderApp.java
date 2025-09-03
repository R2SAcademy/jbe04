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
import java.util.Optional;
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
                System.out.println("9. Update order status");
                System.out.println("10. Filter orders");
                System.out.println("0. Exit");

                System.out.print("\nEnter your choice: ");
                int choice = readInt(sc);

                try {
                    switch (choice) {
                        case 1 -> createOrderFlow(sc, service);
                        case 2 -> addItemFlow(sc, service);
                        case 3 -> updateItemFlow(sc, service);
                        case 4 -> removeItemFlow(sc, service);
                        case 5 -> viewItemsFlow(sc, service);
                        case 6 -> findOrderFlow(sc, service);
                        case 7 -> listOrdersFlow(service);
                        case 8 -> deleteOrderFlow(sc, service);
                        case 9 -> updateOrderStatusFlow(sc, service);
                        case 10 -> filterOrdersFlow(sc, service);
                        case 0 -> {
                            System.out.println("Exiting. Goodbye!");
                            return;
                        }
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

    // ========== Flows ==========

    private static void createOrderFlow(Scanner sc, OrderService service) throws DAOException {
        Order order = new Order();

        System.out.print("Customer ID: ");
        order.setCustomerId(readInt(sc));
        order.setOrderDate(LocalDate.now());
        order.setStatus(readStatus(sc));

        System.out.print("Discount ID (or blank): ");
        String d = sc.nextLine().trim();
        order.setDiscountId(d.isEmpty() ? null : Integer.parseInt(d));

        List<OrderItem> items = new ArrayList<>();
        System.out.print("Add items now? (YES/NO): ");

        if (sc.nextLine().trim().equalsIgnoreCase("YES")) {
            do {
                items.add(readItem(sc));
                System.out.print("Add another item? (YES/NO): ");
            } while (sc.nextLine().trim().equalsIgnoreCase("YES"));
        }

        Order saved = service.createOrder(order, items);
        System.out.println("Order created: " + saved);
    }

    private static void addItemFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);
        OrderItem item = readItem(sc);

        service.addItemToOrder(orderId, item);
        System.out.println("Item added successfully.");

        var items = service.listItems(orderId);
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            System.out.println("\nCurrent items in Order #" + orderId + ":");
            System.out.println("------------------------------------------");
            System.out.printf("%-10s %-25s %-10s\n", "ProductID", "Product Name", "Quantity");
            items.forEach(it -> {
                System.out.printf("%-10d %-25s %-10d\n",
                        it.getProductId(), it.getProductName(), it.getQuantity());
            });
        }
    }

    private static void updateItemFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);

        System.out.print("Product ID: ");
        int productId = readInt(sc);

        Optional<OrderItem> optItem = service.findItemby(orderId, productId);
        if (optItem.isEmpty()) {
            System.out.println("Item not found in this order.");
            return;
        }
        OrderItem current = optItem.get();

        System.out.print("New quantity (Enter to skip): ");
        String qInput = sc.nextLine().trim();
        int quantity = qInput.isEmpty() ? current.getQuantity() : Integer.parseInt(qInput);

        System.out.print("New price (Enter to skip): ");
        String pInput = sc.nextLine().trim();
        BigDecimal price = pInput.isEmpty() ? current.getPrice() : new BigDecimal(pInput);

        OrderItem item = new OrderItem(orderId, productId, quantity, price);
        service.updateItem(item);
        System.out.println("Item updated.");
    }

    private static void removeItemFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);

        System.out.print("Product ID: ");
        int productId = readInt(sc);

        boolean ok = service.removeItem(orderId, productId);
        System.out.println(ok ? "Item removed." : "Nothing to remove.");
    }

    private static void viewItemsFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int orderId = readInt(sc);
        var items = service.listItems(orderId);

        if (items.isEmpty()) {
            System.out.println("No items.");
        } else {
            items.forEach(System.out::println);
        }
    }

    private static void findOrderFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int id = readInt(sc);
        Optional<Order> opt = service.findOrderById(id);

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
        } else {
            orders.forEach(System.out::println);
        }
    }

    private static void deleteOrderFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int id = readInt(sc);
        boolean ok = service.deleteOrder(id);

        System.out.println(ok ? "Order deleted." : "Order not found.");
    }

    private static void updateOrderStatusFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Order ID: ");
        int id = readInt(sc);
        OrderStatus status = readStatus(sc);
        service.updateOrderStatus(id, status);

        System.out.println("Status updated.");
    }

    private static void filterOrdersFlow(Scanner sc, OrderService service) throws DAOException {
        System.out.print("Customer ID (or blank): ");
        String c = sc.nextLine().trim();
        Integer customerId = c.isEmpty() ? null : Integer.parseInt(c);

        System.out.print("Status (1=PENDING, 2=CONFIRMED, 3=SHIPPED, 4=CANCELLED, blank=all): ");
        String s = sc.nextLine().trim();
        OrderStatus status = s.isEmpty() ? null : OrderStatus.fromCode(Integer.parseInt(s));

        System.out.print("Start date (yyyy-mm-dd or blank): ");
        String start = sc.nextLine().trim();
        start = start.isEmpty() ? null : start;

        System.out.print("End date (yyyy-mm-dd or blank): ");
        String end = sc.nextLine().trim();
        end = end.isEmpty() ? null : end;

        var list = service.filterOrders(customerId, status, start, end);
        if (list.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            list.forEach(System.out::println);
        }
    }

    // ========= Helpers =========

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Please enter an integer: ");
            }
        }
    }

    private static BigDecimal readBig(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }

    private static OrderStatus readStatus(Scanner sc) {
        System.out.print("Status (1=PENDING, 2=CONFIRMED, 3=SHIPPED, 4=CANCELLED) [Enter = PENDING]: ");
        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            return OrderStatus.PENDING;
        }
        try {
            int code = Integer.parseInt(input);
            return OrderStatus.fromCode(code);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to PENDING.");
            return OrderStatus.PENDING;
        }
    }

    private static OrderItem readItem(Scanner sc) {
        System.out.print("Product ID: ");
        int productId = readInt(sc);

        System.out.print("Quantity: ");
        int quantity = readInt(sc);

        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }
}