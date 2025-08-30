package project.dao;

import project.entity.Cart;
import project.entity.CartItem;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    // Kiểm tra user tồn tại
    private boolean isUserExists(int userId, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Cart getOrCreateCart(int userId) throws DAOException {
        String selectCart = "SELECT cart_id FROM carts WHERE user_id = ?";
        String insertCart = "INSERT INTO carts(user_id) VALUES (?)";

        try (Connection conn = JDBCUtil.getConnection()) {

            // Kiểm tra user tồn tại
            if (!isUserExists(userId, conn)) {
                throw new DAOException("User ID " + userId + " does not exist. Cannot create cart.", null);
            }

            // Kiểm tra cart đã tồn tại
            try (PreparedStatement ps = conn.prepareStatement(selectCart)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int cartId = rs.getInt("cart_id");
                        return new Cart(cartId, userId);
                    }
                }
            }

            // Tạo mới cart
            try (PreparedStatement ps = conn.prepareStatement(insertCart, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    throw new DAOException("Creating cart failed, no rows affected.", null);
                }
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int cartId = rs.getInt(1);
                        return new Cart(cartId, userId);
                    } else {
                        throw new DAOException("Creating cart failed, no ID obtained.", null);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error in getOrCreateCart: " + e.getMessage(), e);
        }
    }

    @Override
    public void addProductToCart(int userId, int productId, int quantity) throws DAOException {
        try (Connection conn = JDBCUtil.getConnection()) {
            Cart cart = getOrCreateCart(userId);

            // Kiểm tra sản phẩm trong cart
            String checkSql = "SELECT quantity FROM cart_items WHERE cart_id=? AND product_id=?";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, cart.getCartId());
                ps.setInt(2, productId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int newQty = rs.getInt("quantity") + quantity;
                    String updateSql = "UPDATE cart_items SET quantity=? WHERE cart_id=? AND product_id=?";
                    try (PreparedStatement ps2 = conn.prepareStatement(updateSql)) {
                        ps2.setInt(1, newQty);
                        ps2.setInt(2, cart.getCartId());
                        ps2.setInt(3, productId);
                        ps2.executeUpdate();
                        return;
                    }
                }
            }

            // Thêm mới
            String insertSql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, cart.getCartId());
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DAOException("Error adding product to cart", e);
        }
    }

    @Override
    public List<CartItem> getCartItems(int userId) throws DAOException {
        List<CartItem> items = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection()) {
            Cart cart = getOrCreateCart(userId);
            String sql = "SELECT cart_item_id, product_id, quantity FROM cart_items WHERE cart_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, cart.getCartId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    items.add(new CartItem(
                            rs.getInt("cart_item_id"),
                            cart.getCartId(),
                            rs.getInt("product_id"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving cart items", e);
        }
        return items;
    }

    @Override
    public void updateCartItem(int userId, int productId, int quantity) throws DAOException {
        try (Connection conn = JDBCUtil.getConnection()) {
            Cart cart = getOrCreateCart(userId);

            if (quantity <= 0) {
                removeCartItem(userId, productId);
                return;
            }

            String sql = "UPDATE cart_items SET quantity=? WHERE cart_id=? AND product_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, quantity);
                ps.setInt(2, cart.getCartId());
                ps.setInt(3, productId);
                int rows = ps.executeUpdate();
                if (rows == 0) throw new DAOException("Item not found in cart", null);
            }

        } catch (SQLException e) {
            throw new DAOException("Error updating cart item", e);
        }
    }

    @Override
    public void removeCartItem(int userId, int productId) throws DAOException {
        try (Connection conn = JDBCUtil.getConnection()) {
            Cart cart = getOrCreateCart(userId);
            String sql = "DELETE FROM cart_items WHERE cart_id=? AND product_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, cart.getCartId());
                ps.setInt(2, productId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Error removing cart item", e);
        }
    }

    @Override
    public void clearCart(int userId) throws DAOException {
        try (Connection conn = JDBCUtil.getConnection()) {
            Cart cart = getOrCreateCart(userId);
            String sql = "DELETE FROM cart_items WHERE cart_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, cart.getCartId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Error clearing cart", e);
        }
    }
}
