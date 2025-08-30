package project.form;

import project.dao.ShipmentDAO;
import project.entity.Shipment;
import project.util.ScannerUtil;

import java.util.Scanner;

public class ShipmentForm {
    public static Shipment inputShipment() {
        Shipment shipment = new Shipment();

        shipment.setOrderId(ScannerUtil.readNonNegativeInt("Enter order id: "));
        shipment.setCarrier(ScannerUtil.readNonEmptyString("Enter carrier: "));
        shipment.setTrackingNumber("GNH" + System.currentTimeMillis());
        shipment.setShippedDate(ScannerUtil.inputNonPastDate("Enter shipping day: "));

        return shipment;
    }

    public static Shipment inputUpdateShipment() {
        Shipment shipment = new Shipment();

        shipment.setShipmentId(ScannerUtil.readNonNegativeInt("Enter shipment ID to update: "));
        shipment.setStatus(ScannerUtil.readNonEmptyString("Enter new status: "));
        shipment.setShippedDate(ScannerUtil.inputNonPastDate("Enter new shipping date (YYYY-MM-DD): "));
        shipment.setTrackingNumber(ScannerUtil.readNonEmptyString("Enter new tracking number: "));

        return shipment;
    }
}
