package project.dao;

import project.entity.Shipment;
import project.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface ShipmentDAO {
    void createShipment(Shipment shipment) throws DAOException;
    List<Shipment> getShipmentsByTrackingNumber(String trackingNumber) throws DAOException, SQLException;
    List<Shipment> getShipmentsByOrderId(int orderId) throws DAOException, SQLException;
    Shipment getAllShipments (int shipmentId) throws DAOException, SQLException;
    void updateShipment(Shipment shipment) throws DAOException;
    void deleteShipment(int shipmentId) throws DAOException;
}
