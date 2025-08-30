package project.util;

public class Constants {
    public static final String BRAND_HEADER = String.format("%-10s | %-30s", "Brand ID", "Brand Name");
    public static final String BRAND_ROW_FORMAT = "%-10d | %-30s";
    public static final String SHIPMENT_HEADER = String.format(
            "%-15s | %-10s | %-20s | %-25s | %-15s | %-20s | %-20s",
            "Shipment ID", "Order ID", "Carrier", "Tracking No", "Shipped Date", "Status", "Customer Name"
    );
}