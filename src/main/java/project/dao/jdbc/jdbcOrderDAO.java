package project.dao.jdbc;

import project.dao.OrderDAO;
import project.entity.Order;
import project.entity.OrderStatus;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class jdbcOrderDAO implements OrderDAO {
    private final Connection connection;

    public jdbcOrderDAO(Connection connection) {
        this.connection = connection;
    }

    private static Date sqlDate(LocalDate d, LocalDate fallback) {
        return Date.valueOf(d != null ? d : fallback);
    }
    private static void setNullableInt(PreparedStatement ps, int idx, Integer v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.INTEGER); else ps.setInt(idx, v);
    }
    private static void setEnum(PreparedStatement ps, int idx, OrderStatus st, OrderStatus def) throws SQLException {
        ps.setString(idx, (st != null ? st : def).name());
    }
    private Order map(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("order_id"));                         // <-- dùng setId
        o.setCustomerId(rs.getInt("customer_id"));
        Date d = rs.getDate("order_date");
        o.setOrderDate(d == null ? null : d.toLocalDate());
        Object disc = rs.getObject("discount_id");
        o.setDiscountId(disc == null ? null : (Integer) disc);
        String st = rs.getString("status");
        o.setStatus(st == null ? null : OrderStatus.valueOf(st));
        o.setTotalAmount(rs.getBigDecimal("total_amount"));
        return o;
    }

    private void bindCommon(PreparedStatement ps, Order o, boolean forInsert) throws SQLException {
        ps.setInt(1, o.getCustomerId());
        ps.setDate(2, forInsert ? sqlDate(o.getOrderDate(), LocalDate.now())
                : (o.getOrderDate() == null ? null : Date.valueOf(o.getOrderDate())));
        setNullableInt(ps, 3, o.getDiscountId());
        setEnum(ps, 4, o.getStatus(), OrderStatus.PENDING);
        ps.setBigDecimal(5, o.getTotalAmount() == null ? BigDecimal.ZERO : o.getTotalAmount());
    }

    @Override
    public Order insert(Order order) throws DAOException {
        final String sql = """
            INSERT INTO orders (customer_id, order_date, discount_id, status, total_amount)
            VALUES (?,?,?,?,?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindCommon(ps, order, true);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) order.setId(keys.getInt(1));    // <-- setId
            }
            return order;
        } catch (SQLException e) {
            throw new DAOException("Insert order failed", e);
        }
    }

    @Override
    public boolean update(Order order) throws DAOException {
        final String sql = """
        UPDATE orders 
        SET customer_id=?, order_date=?, discount_id=?, status=?, total_amount=? 
        WHERE order_id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            bindCommon(ps, order, false);
            ps.setInt(6, order.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new DAOException("Update order failed", e);
        }
    }

    @Override
    public boolean delete(int orderId) throws DAOException {
        final String sql = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new DAOException("Delete order failed", e);
        }
    }

    @Override
    public Optional<Order> findById(int orderId) throws DAOException {

        final String sql = "SELECT * FROM orders WHERE order_id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Find order failed", e);
        }
    }

    @Override
    public List<Order> findAll() throws DAOException {
        final String sql = "SELECT * FROM orders ORDER BY order_id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Order> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;

        } catch (SQLException e) {
            throw new DAOException("List orders failed", e);
        }
    }

    @Override
    public List<Order> findByCustomerId(int customerId) throws DAOException {
        final String sql = "SELECT * FROM orders WHERE customer_id=? ORDER BY order_id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Order> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }

        } catch (SQLException e) {
            throw new DAOException("List orders by customer failed", e);
        }
    }
}
