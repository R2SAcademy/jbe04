package project.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private Integer id;
    private int customerId;
    private LocalDate orderDate;
    private Integer discountId;
    private OrderStatus status;          // enum tránh lỗi string
    private BigDecimal totalAmount;

    public Order() {

    }

    public Order(Integer id, int customerId, LocalDate orderDate,
                 OrderStatus status, Integer discountId, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.discountId = discountId;
        this.totalAmount = totalAmount;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public Integer getDiscountId() { return discountId; }
    public void setDiscountId(Integer discountId) { this.discountId = discountId; }

    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", discountId=" + discountId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
