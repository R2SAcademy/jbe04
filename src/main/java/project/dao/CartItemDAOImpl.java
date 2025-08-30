package project.dao;

import project.dao.CartItemDAO;
import project.entity.CartItem;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public void addOrUpdateCartItem(int cartId, int productId, int quantity) throws DAOException {
        String checkSql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_id = ? AND product_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, cartId);
            checkPs.setInt(2, productId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, quantity);
                    updatePs.setInt(2, cartId);
                    updatePs.setInt(3, productId);
                    updatePs.executeUpdate();
                }
            } else {
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, cartId);
                    insertPs.setInt(2, productId);
                    insertPs.setInt(3, quantity);
                    insertPs.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error adding/updating cart item", e);
        }
    }

    @Override
    public List<CartItem> findByCartId(int cartId) throws DAOException {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                items.add(new CartItem(
                        rs.getInt("cart_item_id"),
                        rs.getInt("cart_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching cart items", e);
        }
        return items;
    }

    @Override
    public void updateQuantity(int cartItemId, int quantity) throws DAOException {
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (quantity == 0) {
                deleteByCartItemId(cartItemId);
                return;
            }

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error updating quantity", e);
        }
    }

    private void deleteByCartItemId(int cartItemId) throws DAOException {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error deleting cart item", e);
        }
    }

    @Override
    public void deleteItem(int cartId, int productId) throws DAOException {
        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error deleting item from cart", e);
        }
    }

    @Override
    public void clearCart(int cartId) throws DAOException {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error clearing cart", e);
        }
    }
}
