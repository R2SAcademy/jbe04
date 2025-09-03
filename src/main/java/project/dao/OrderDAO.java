package project.dao;

import project.entity.Order;
import project.entity.OrderStatus;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Order insert(Order order) throws DAOException;

    boolean update(Order order) throws DAOException;

    boolean delete(int orderId) throws DAOException;

    Optional<Order> findById(int orderId) throws DAOException;

    List<Order> findAll() throws DAOException;

    // filter
    List<Order> filter(Integer customerId, OrderStatus status,
                       String startDate, String endDate) throws DAOException;

    BigDecimal getDiscountPercentage(Integer discountId);

    BigDecimal getDiscountPercentage(int discountId) throws DAOException;

    void recalcAllOrders(
    );
}
