package project.entity;

public class Wishlist {
    private int wishlistId;
    private int customerId;
    private int productId;

    public Wishlist() {}

    public Wishlist(int wishlistId, int customerId, int productId) {
        this.wishlistId = wishlistId;
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }

}
