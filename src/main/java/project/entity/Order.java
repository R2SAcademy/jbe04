package project.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private int id;
    private int customerId;
    private LocalDate orderDate;
    private OrderStatus status;
    private Integer discountId;      // optional
    private BigDecimal totalAmount;  // auto calculated

    public Order() {
    }

    public Order(int id, int customerId, LocalDate orderDate,
                 OrderStatus status, Integer discountId, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.discountId = discountId;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount; }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

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
