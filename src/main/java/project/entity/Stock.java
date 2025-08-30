package project.entity;

public class Stock {
    int stockId;
    Integer storeId;
    Integer productId;
    Integer quantity;

    public Stock() {
    }

    public Stock(int stockId, Integer storeId, Integer productId, int quantity) {
        this.stockId = stockId;
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", storeId=" + storeId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
