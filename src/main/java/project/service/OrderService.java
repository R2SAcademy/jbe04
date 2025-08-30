package project.service;

import project.dao.OrderDAO;
import project.dao.OrderItemDAO;
import project.entity.Order;
import project.entity.OrderItem;
import project.exception.DAOException;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO itemDAO;

    public OrderService(OrderDAO orderDAO, OrderItemDAO itemDAO) {
        this.orderDAO = orderDAO;
        this.itemDAO = itemDAO;
    }

    public Order createOrder(Order order, List<OrderItem> items) throws DAOException {
        Order saved = orderDAO.insert(order);
        for (OrderItem it : items) {
            it.setOrderId(saved.getId());
            itemDAO.addItem(it);
        }
        return saved;
    }

    public void updateOrder(Order order) throws DAOException {
        orderDAO.update(order);
    }

    public boolean deleteOrder(int orderId) throws DAOException {
        itemDAO.removeAllByOrder(orderId);

        return orderDAO.delete(orderId);
    }

    public Optional<Order> findOrderById(int orderId) throws DAOException {
        return orderDAO.findById(orderId);
    }

    public List<Order> findAllOrders() throws DAOException {
        return orderDAO.findAll();
    }

    public void addItemToOrder(int orderId, OrderItem it) throws DAOException {
        it.setOrderId(orderId);
        itemDAO.addItem(it);
    }

    public void updateItem(OrderItem it) throws DAOException {
        itemDAO.updateItem(it);
    }

    public boolean removeItem(int orderId, int productId) throws DAOException {
        return itemDAO.removeItem(orderId, productId);
    }

    public List<OrderItem> listItems(int orderId) throws DAOException {
        return itemDAO.findByOrderId(orderId);
    }
}
