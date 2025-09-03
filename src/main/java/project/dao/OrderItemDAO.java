package project.dao;

import project.entity.OrderItem;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {
    void addItem(OrderItem item) throws DAOException;

    boolean update(OrderItem item) throws DAOException;

    boolean remove(int orderId, int productId) throws DAOException;

    void removeAllByOrder(int orderId) throws DAOException;

    Optional<OrderItem> find(int orderId, int productId) throws DAOException;

    List<OrderItem> findAllByOrder(int orderId) throws DAOException;

    BigDecimal calculateTotalForOrder(int orderId) throws DAOException;
}
