package project.dao.jdbc;

import project.dao.OrderDAO;
import project.entity.Order;
import project.entity.OrderStatus;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class jdbcOrderDAO implements OrderDAO {
    private final Connection conn;

    public jdbcOrderDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Order insert(Order order) throws DAOException {
        String sql = "INSERT INTO orders (customer_id, order_date, status, discount_id, total_amount) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getCustomerId());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setInt(3, order.getStatus().getCode());

            if (order.getDiscountId() != null) {
                ps.setInt(4, order.getDiscountId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setBigDecimal(5, order.getTotalAmount());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
            }
            return order;
        } catch (SQLException e) {
            throw new DAOException("Error inserting order", e);
        }
    }

    @Override
    public boolean update(Order order) throws DAOException {
        String sql = "UPDATE orders SET customer_id=?, order_date=?, status=?, discount_id=?, total_amount=? " +
                "WHERE order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getCustomerId());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setInt(3, order.getStatus().getCode());

            if (order.getDiscountId() != null) {
                ps.setInt(4, order.getDiscountId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setBigDecimal(5, order.getTotalAmount());
            ps.setInt(6, order.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating order", e);
        }
    }

    @Override
    public boolean delete(int orderId) throws DAOException {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error deleting order", e);
        }
    }

    @Override
    public Optional<Order> findById(int orderId) throws DAOException {
        String sql = "SELECT * FROM orders WHERE order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DAOException("Error finding order by id", e);
        }
    }

    @Override
    public List<Order> findAll() throws DAOException {
        String sql = "SELECT * FROM orders";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error finding all orders", e);
        }
    }

    @Override
    public List<Order> filter(Integer customerId, OrderStatus status,
                              String startDate, String endDate) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT * FROM orders WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (customerId != null) {
            sql.append(" AND customer_id=?");
            params.add(customerId);
        }
        if (status != null) {
            sql.append(" AND status=?");
            params.add(status.getCode());
        }
        if (startDate != null) {
            sql.append(" AND order_date>=?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null) {
            sql.append(" AND order_date<=?");
            params.add(Date.valueOf(endDate));
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Order> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DAOException("Error filtering orders", e);
        }
    }

    @Override
    public BigDecimal getDiscountPercentage(Integer discountId) {
        return null;
    }

    @Override
    public BigDecimal getDiscountPercentage(int discountId) throws DAOException {
        String sql = "SELECT percentage FROM discounts WHERE discount_id=? AND (expires_at IS NULL OR expires_at >= CURDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, discountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return BigDecimal.valueOf(rs.getInt("percentage"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException("Error fetching discount percentage", e);
        }
    }

    @Override
    public void recalcAllOrders() {
    }


    private Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("order_id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setOrderDate(rs.getDate("order_date").toLocalDate());
        order.setStatus(OrderStatus.fromCode(rs.getInt("status")));
        int discountId = rs.getInt("discount_id");

        if (!rs.wasNull()) {
            order.setDiscountId(discountId);
        }
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        return order;
    }
}
