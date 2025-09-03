package project.service;

import project.dao.OrderDAO;
import project.dao.OrderItemDAO;
import project.entity.Order;
import project.entity.OrderItem;
import project.entity.OrderStatus;
import project.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO itemDAO;

    public OrderService(OrderDAO orderDAO, OrderItemDAO itemDAO) {
        this.orderDAO = orderDAO;
        this.itemDAO = itemDAO;
    }

    // ======= ORDER ======
    /** Create new order (with optional items) */
    public Order createOrder(Order order, List<OrderItem> items) throws DAOException {
        Order saved = orderDAO.insert(order);
        for (OrderItem it : items) {
            it.setOrderId(saved.getId());
            itemDAO.addItem(it); // DAO sẽ lấy price từ products
        }

        recalcOrderTotal(saved.getId());
        return orderDAO.findById(saved.getId()).orElse(saved);
    }

    /** Find order by ID */
    public Optional<Order> findOrderById(int orderId) throws DAOException {
        return orderDAO.findById(orderId);
    }

    /** List all orders */
    public List<Order> findAllOrders() throws DAOException {
        return orderDAO.findAll();
    }

    /** Filter orders (customerId, status, date range) */
    public List<Order> filterOrders(Integer customerId, OrderStatus status,
                                    String startDate, String endDate) throws DAOException {
        return orderDAO.filter(customerId, status, startDate, endDate);
    }

    /** Update order status */
    public void updateOrderStatus(int orderId, OrderStatus newStatus) throws DAOException {
        Optional<Order> orderOpt = orderDAO.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new DAOException("Order not found");
        }
        Order order = orderOpt.get();

        // business rule
        if (order.getStatus() == OrderStatus.SHIPPED && newStatus == OrderStatus.PENDING) {
            throw new DAOException("Cannot revert shipped order to pending.");
        }

        order.setStatus(newStatus);
        orderDAO.update(order);
    }

    /** Delete order (with items) */
    public boolean deleteOrder(int orderId) throws DAOException {
        Optional<Order> orderOpt = orderDAO.findById(orderId);
        if (orderOpt.isEmpty())
            return false;

        Order order = orderOpt.get();
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new DAOException("Cannot delete finalized order.");
        }

        itemDAO.removeAllByOrder(orderId);
        return orderDAO.delete(orderId);
    }

    //======= ORDER ITEM =======

    /** Add item into an order */
    public void addItemToOrder(int orderId, OrderItem item) throws DAOException {
        item.setOrderId(orderId);
        itemDAO.addItem(item);
        recalcOrderTotal(orderId);
    }

    /** Update item **/
    public void updateItem(OrderItem item) throws DAOException {
        if (itemDAO.find(item.getOrderId(), item.getProductId()).isEmpty()) {
            throw new DAOException("Item not found in order.");
        }
        itemDAO.update(item);
        recalcOrderTotal(item.getOrderId());
    }

    /** Remove item */
    public boolean removeItem(int orderId, int productId) throws DAOException {
        Optional<Order> orderOpt = orderDAO.findById(orderId);
        if (orderOpt.isEmpty())
            return false;

        Order order = orderOpt.get();
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new DAOException("Cannot remove item from finalized order.");
        }

        boolean ok = itemDAO.remove(orderId, productId);
        if (ok) {
            recalcOrderTotal(orderId);
        }
        return ok;
    }

    /** List all items of an order */
    public List<OrderItem> listItems(int orderId) throws DAOException {
        return itemDAO.findAllByOrder(orderId);
    }

    // ======== HELPER ========

    /** Tính lại tổng tiền ( bao gồm cả discount ) */
    private void recalcOrderTotal(int orderId) throws DAOException {
        // subtotal from order_items
        BigDecimal subtotal = itemDAO.calculateTotalForOrder(orderId);

        // load order
        Order order = orderDAO.findById(orderId).orElseThrow(
                () -> new DAOException("Order not found when recalculating total")
        );

        // áp discount nếu có
        BigDecimal total = subtotal;
        if (order.getDiscountId() != null) {
            BigDecimal discountPercent = orderDAO.getDiscountPercentage(order.getDiscountId());
            if (discountPercent != null) {
                total = subtotal.multiply(BigDecimal.valueOf(100).subtract(discountPercent))
                        .divide(BigDecimal.valueOf(100));
            }
        }

        // update
        order.setTotalAmount(total);
        orderDAO.update(order);
    }

    public Optional<OrderItem> findItemby(int orderId, int productId) throws DAOException {
        return itemDAO.find(orderId, productId);
    }

    public void refreshAllTotals() throws DAOException {
        orderDAO.recalcAllOrders();
    }
}