package project.entity;

import java.time.LocalDate;

public class Shipment {
    private int shipmentId;
    private int orderId;
    private String carrier;
    private String trackingNumber;
    private LocalDate shippedDate;
    private String customerName;
    private String status;

    public Shipment(){}

    public Shipment(int shipmentId, int orderId, String carrier, String trackingNumber, LocalDate shippedDate) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.shippedDate = shippedDate;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    @Override
    public String toString() {
        return String.format("--- Shipping Details ---%n" +
                        "  Shipment ID:    %d%n" +
                        "  Order ID:       %d%n" +
                        "  Carrier:        %s%n" +
                        "  Tracking No:    %s%n" +
                        "  Shipped Date:   %s%n" +
                        "  Status:         %s%n" +
                        "  Customer Name:  %s%n" +
                        "----------------------",
                shipmentId, orderId, carrier, trackingNumber, shippedDate, status, customerName);
    }
}
