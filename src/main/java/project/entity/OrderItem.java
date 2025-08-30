package project.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(int orderId, int productId, int quantity, BigDecimal price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId; }

    public void setOrderId(int orderId) {
        this.orderId = orderId; }

    public int getProductId() {
        return productId; }

    public void setProductId(int productId) {
        this.productId = productId; }

    public int getQuantity() {
        return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity; }

    public BigDecimal getPrice() {
        return price; }

    public void setPrice(BigDecimal price) {
        this.price = price; }

    public BigDecimal lineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity)); }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OrderItem that))
            return false;

        return orderId == that.orderId && productId == that.productId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId); }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", qty=" + quantity +
                ", price=" + price +
                ", lineTotal=" + lineTotal() +
                '}';
    }

    public void setOrderItemId(int orderItemId) {
    }
}