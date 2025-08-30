package project.form;

import project.entity.Wishlist;
import project.util.ScannerUtil;

public class WishlistForm {
    public static Wishlist inputNewWishlist() {
        int customerId = ScannerUtil.readInt("Enter customer ID: ");
        int productId = ScannerUtil.readInt("Enter product ID: ");
        return new Wishlist(0, customerId, productId);
    }

    public static int inputWishlistId(String action) {
        return ScannerUtil.readInt("Enter wishlist ID to " + action + ": ");
    }
}
