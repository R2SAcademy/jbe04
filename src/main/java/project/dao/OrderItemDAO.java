package project.dao;

import project.entity.OrderItem;
import project.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {
    OrderItem insert(OrderItem item) throws DAOException;

    void addItem(OrderItem item) throws DAOException;             // cộng dồn nếu có cùng product trong 1 order
    void updateItem(OrderItem item) throws DAOException;          // theo (orderId, productId)
    boolean removeItem(int orderId, int productId) throws DAOException;

    Optional<OrderItem> findOne(int orderId, int productId) throws DAOException;
    List<OrderItem> findByOrderId(int orderId) throws DAOException;

    boolean existsForOrder(int orderId) throws DAOException;
    void removeAllByOrder(int orderId) throws DAOException;

    Optional<OrderItem> findById(int orderItemId) throws DAOException;
}
