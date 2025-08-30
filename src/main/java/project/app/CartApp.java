// CartApp.java
package project.app;

import project.dao.CartDAO;
import project.dao.CartDAOImpl;
import project.entity.CartItem;
import project.form.CartForm;
import project.util.Constants;

import java.util.List;
import java.util.Scanner;

public class CartApp {
        public static void run() {
        Scanner sc = new Scanner(System.in);
        CartForm form = new CartForm();
        CartDAO cartDAO = new CartDAOImpl();
        while (true) {
            System.out.println(Constants.CART_MENU  );
            System.out.print("Chọn chức năng: ");
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        int userId = form.inputUserId();
                        int productId = form.inputProductId();
                        int qty = form.inputQuantity();
                        cartDAO.addProductToCart(userId, productId, qty);
                        System.out.println("✅ Đã thêm sản phẩm vào giỏ.");
                    }
                    case "2" -> {
                        int userId = form.inputUserId();
                        List<CartItem> items = cartDAO.getCartItems(userId);
                        if (items.isEmpty()) {
                            System.out.println("Giỏ hàng trống.");
                        } else {
                            System.out.println("Giỏ hàng của user " + userId + ":");
                            for (CartItem item : items) {
                                System.out.printf("- productId=%d, quantity=%d%n",
                                        item.getProductId(), item.getQuantity());
                            }
                        }
                    }
                    case "3" -> {
                        int userId = form.inputUserId();
                        int productId = form.inputProductId();
                        int qty = form.inputQuantity();
                        cartDAO.updateCartItem(userId, productId, qty);
                        System.out.println("✅ Đã cập nhật sản phẩm.");
                    }
                    case "4" -> {
                        int userId = form.inputUserId();
                        int productId = form.inputProductId();
                        cartDAO.removeCartItem(userId, productId);
                        System.out.println("✅ Đã xóa sản phẩm khỏi giỏ.");
                    }
                    case "5" -> {
                        int userId = form.inputUserId();
                        cartDAO.clearCart(userId);
                        System.out.println("✅ Đã xóa toàn bộ giỏ hàng.");
                    }
                    case "0" -> {
                        System.out.println("Thoát chương trình.");
                        return;
                    }
                    default -> System.out.println("❌ Lựa chọn không hợp lệ.");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new CartApp().run();
    }
}
