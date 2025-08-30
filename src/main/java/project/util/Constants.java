package project.util;

public class Constants {
    public static final String BRAND_HEADER = String.format("%-10s | %-30s", "Brand ID", "Brand Name");
    public static final String BRAND_ROW_FORMAT = "%-10d | %-30s";

    // ===== Dashboard menu =====
    public static final int MENU_BRAND = 1;
    public static final int MENU_WISHLIST_DISCOUNT = 2;
    public static final int MENU_EXIT = 0;

    // ===== Brand menu =====
    public static final int BRAND_ADD = 1;
    public static final int BRAND_UPDATE = 2;
    public static final int BRAND_DELETE = 3;
    public static final int BRAND_VIEW = 4;
    public static final int BRAND_BACK = 0;

    // ===== Wishlist menu =====
    public static final int WISHLIST_ADD = 1;
    public static final int WISHLIST_DELETE = 2;
    public static final int WISHLIST_VIEW = 3;
    public static final int WISHLIST_BACK = 0;

    // ===== Discount menu =====
    public static final int DISCOUNT_ADD = 1;
    public static final int DISCOUNT_UPDATE = 2;
    public static final int DISCOUNT_DELETE = 3;
    public static final int DISCOUNT_VIEW = 4;
    public static final int DISCOUNT_BACK = 0;
}