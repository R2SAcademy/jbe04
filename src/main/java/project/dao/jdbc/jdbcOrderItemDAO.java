package project.dao.jdbc;

import project.dao.OrderItemDAO;
import project.entity.OrderItem;
import project.exception.DAOException;

import java.sql.*;
import java.util.*;

public class jdbcOrderItemDAO implements OrderItemDAO {
    private final Connection connection;

    public jdbcOrderItemDAO(Connection connection) {
        this.connection = connection;
    }

    private OrderItem map(ResultSet rs) throws SQLException {
        OrderItem i = new OrderItem();
        i.setOrderItemId(rs.getInt("order_item_id"));
        i.setOrderId(rs.getInt("order_id"));
        i.setProductId(rs.getInt("product_id"));
        i.setQuantity(rs.getInt("quantity"));
        i.setPrice(rs.getBigDecimal("price"));
        return i;
    }

    @Override
    public OrderItem insert(OrderItem item) throws DAOException {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) item.setOrderItemId(keys.getInt(1));
            }
            return item;
        } catch (SQLException e) {
            throw new DAOException("Insert order item failed", e);
        }
    }

    @Override
    public void addItem(OrderItem item) throws DAOException {

    }

    @Override
    public void updateItem(OrderItem item) throws DAOException {

    }

    @Override
    public boolean removeItem(int orderId, int productId) throws DAOException {
        return false;
    }

    @Override
    public Optional<OrderItem> findOne(int orderId, int productId) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<OrderItem> findByOrderId(int orderId) throws DAOException {
        return List.of();
    }

    @Override
    public boolean existsForOrder(int orderId) throws DAOException {
        return false;
    }

    @Override
    public void removeAllByOrder(int orderId) throws DAOException {

    }

    @Override
    public Optional<OrderItem> findById(int orderItemId) throws DAOException {
        String sql = "SELECT * FROM order_items WHERE order_item_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Find order item failed", e);
        }
    }

    // TODO: update, delete, findByOrderId
}
