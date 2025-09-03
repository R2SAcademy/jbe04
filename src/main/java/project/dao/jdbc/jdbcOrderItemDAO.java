package project.dao.jdbc;

import project.dao.OrderItemDAO;
import project.entity.OrderItem;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class jdbcOrderItemDAO implements OrderItemDAO {
    private final Connection conn;

    public jdbcOrderItemDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addItem(OrderItem item) throws DAOException {
        try {
            // 1. Lấy giá sản phẩm từ products
            String sqlPrice = "SELECT price FROM products WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlPrice)) {
                ps.setInt(1, item.getProductId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        item.setPrice(rs.getBigDecimal("price"));
                    } else {
                        throw new DAOException("Cannot add item: product_id " + item.getProductId() + " does not exist.");
                    }
                }
            }

            // 2. Kiểm tra tồn tại (order_id, product_id)
            String sqlCheck = "SELECT quantity FROM order_items WHERE order_id=? AND product_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
                ps.setInt(1, item.getOrderId());
                ps.setInt(2, item.getProductId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Nếu đã có thì cộng dồn quantity
                        int oldQty = rs.getInt("quantity");
                        int newQty = oldQty + item.getQuantity();

                        String sqlUpdate = "UPDATE order_items SET quantity=?, price=? WHERE order_id=? AND product_id=?";
                        try (PreparedStatement ups = conn.prepareStatement(sqlUpdate)) {
                            ups.setInt(1, newQty);
                            ups.setBigDecimal(2, item.getPrice());
                            ups.setInt(3, item.getOrderId());
                            ups.setInt(4, item.getProductId());
                            ups.executeUpdate();
                            return;
                        }
                    }
                }
            }

            // 3. Nếu chưa có thì insert mới
            String sqlInsert = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, item.getOrderId());
                ps.setInt(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setBigDecimal(4, item.getPrice());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DAOException("Error inserting/updating order item. OrderID="
                    + item.getOrderId() + ", ProductID=" + item.getProductId(), e);
        }
    }

    @Override
    public boolean update(OrderItem item) throws DAOException {
        String sql = "UPDATE order_items SET quantity=?, price=? WHERE order_id=? AND product_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getQuantity());
            ps.setBigDecimal(2, item.getPrice());
            ps.setInt(3, item.getOrderId());
            ps.setInt(4, item.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating order item", e);
        }
    }

    @Override
    public boolean remove(int orderId, int productId) throws DAOException {
        String sql = "DELETE FROM order_items WHERE order_id=? AND product_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error removing order item", e);
        }
    }

    @Override
    public void removeAllByOrder(int orderId) throws DAOException {
        String sql = "DELETE FROM order_items WHERE order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error removing all order items", e);
        }
    }

    @Override
    public Optional<OrderItem> find(int orderId, int productId) throws DAOException {
        String sql = "SELECT * FROM order_items WHERE order_id=? AND product_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DAOException("Error finding order item", e);
        }
    }

    @Override
    public List<OrderItem> findAllByOrder(int orderId) throws DAOException {
        String sql = """
        SELECT oi.order_id, oi.product_id, oi.quantity, oi.price,
               p.name AS product_name
        FROM order_items oi
        JOIN products p ON oi.product_id = p.product_id
        WHERE oi.order_id=?
    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                List<OrderItem> list = new ArrayList<>();
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setProductName(rs.getString("product_name")); // lấy thêm tên sản phẩm
                    list.add(item);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding items by order", e);
        }
    }

    @Override
    public BigDecimal calculateTotalForOrder(int orderId) throws DAOException {
        String sql = "SELECT SUM(quantity * price) AS total FROM order_items WHERE order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return total != null ? total : BigDecimal.ZERO;
                }
                return BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new DAOException("Error calculating total for order", e);
        }
    }

    private OrderItem mapRow(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderId(rs.getInt("order_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getBigDecimal("price"));
        return item;
    }
}