package project.dao;

import project.entity.Brand;
import project.entity.Shipment;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShipmentDAOImpl implements ShipmentDAO {

    @Override
    public void createShipment(Shipment shipment) throws DAOException {
        int index = 1;
        String sql = "INSERT INTO shipments (order_id, carrier, tracking_number, shipped_date) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(index++, shipment.getOrderId());
            preparedStatement.setString(index++, shipment.getCarrier());
            preparedStatement.setString(index++, shipment.getTrackingNumber());
            if (shipment.getShippedDate() != null) {
                preparedStatement.setDate(index++, Date.valueOf(shipment.getShippedDate()));
            } else {
                preparedStatement.setNull(index++, Types.DATE);
            }

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    shipment.setShipmentId(resultSet.getInt(1));
                }
            }
            System.out.println("Shipment created with ID: " + shipment.getShipmentId());
        } catch (SQLException e) {
            throw new DAOException("Create shipment fail", e);
        }
    }

    @Override
    public List<Shipment> getShipmentsByTrackingNumber(String trackingNumber) throws DAOException {
        final String query =
                "SELECT s.shipment_id, s.order_id, s.carrier, s.tracking_number, s.shipped_date, " +
                        "       o.status, c.name AS customer_name " +
                        "FROM shipments s " +
                        "JOIN orders o ON s.order_id = o.order_id " +
                        "JOIN customers c ON o.customer_id = c.customer_id " +
                        "WHERE s.tracking_number = ?";

        List<Shipment> shipments = new ArrayList<>();

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, trackingNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Shipment shipment = new Shipment();
                    shipment.setShipmentId(resultSet.getInt("shipment_id"));
                    shipment.setOrderId(resultSet.getInt("order_id"));
                    shipment.setCarrier(resultSet.getString("carrier"));
                    shipment.setTrackingNumber(resultSet.getString("tracking_number"));
                    java.sql.Date shippedDate = resultSet.getDate("shipped_date");
                    if (shippedDate != null) {
                        shipment.setShippedDate(shippedDate.toLocalDate());
                    } else {
                        shipment.setShippedDate(null);
                    }

                    shipment.setStatus(resultSet.getString("status"));
                    shipment.setCustomerName(resultSet.getString("customer_name"));

                    shipments.add(shipment);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error tracking shipments", e);
        }

        return shipments;
    }


    @Override
    public List<Shipment> getShipmentsByOrderId(int orderId) throws DAOException, SQLException {
        final String query =  "SELECT s.shipment_id, s.order_id, s.carrier, s.tracking_number, s.shipped_date, " +
                "       o.status, c.name AS customer_name " +
                "FROM shipments s " +
                "JOIN orders o ON s.order_id = o.order_id " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "WHERE s.order_id = ?";

        List<Shipment> shipments = new ArrayList<>();

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Shipment shipment = new Shipment();
                    shipment.setShipmentId(resultSet.getInt("shipment_id"));
                    shipment.setOrderId(resultSet.getInt("order_id"));
                    shipment.setCarrier(resultSet.getString("carrier"));
                    shipment.setTrackingNumber(resultSet.getString("tracking_number"));
                    java.sql.Date shippedDate = resultSet.getDate("shipped_date");
                    if (shippedDate != null) {
                        shipment.setShippedDate(shippedDate.toLocalDate());
                    } else {
                        shipment.setShippedDate(null);
                    }

                    shipment.setStatus(resultSet.getString("status"));
                    shipment.setCustomerName(resultSet.getString("customer_name"));

                    shipments.add(shipment);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error tracking shipments", e);
        }

        return shipments;
    }

    @Override
    public Shipment getAllShipments(int shipmentId) throws DAOException, SQLException {
        String sql = "SELECT * FROM shipments WHERE shipment_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shipmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate("shipped_date");
                LocalDate shippedDate = (date != null) ? date.toLocalDate() : null;

                return new Shipment(
                        rs.getInt("shipment_id"),
                        rs.getInt("order_id"),
                        rs.getString("carrier"),
                        rs.getString("tracking_number"),
                        shippedDate
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to find brand by ID.", e);
        }
        return null;
    }

    @Override
    public void updateShipment(Shipment shipment) throws DAOException {
        final String queryUpdateOrder =
                "UPDATE orders SET status = ? WHERE order_id = (SELECT order_id FROM shipments WHERE shipment_id = ?)";

        final String queryUpdateShipment =
                "UPDATE shipments SET shipped_date = ?, tracking_number = ? WHERE shipment_id = ?";

        Connection connection = null;

        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Start the transaction

            try (PreparedStatement preparedStatementShipment = connection.prepareStatement(queryUpdateShipment)) {
                if (shipment.getShippedDate() != null) {
                    preparedStatementShipment.setDate(1, Date.valueOf(shipment.getShippedDate()));
                } else {
                    preparedStatementShipment.setNull(1, Types.DATE);
                }
                preparedStatementShipment.setString(2, shipment.getTrackingNumber());
                preparedStatementShipment.setInt(3, shipment.getShipmentId());
                preparedStatementShipment.executeUpdate();
            }

            try (PreparedStatement preparedStatementOrder = connection.prepareStatement(queryUpdateOrder)) {
                preparedStatementOrder.setString(1, shipment.getStatus());
                preparedStatementOrder.setInt(2, shipment.getShipmentId());
                preparedStatementOrder.executeUpdate();
            }

            connection.commit();
            System.out.println("Shipment with ID " + shipment.getShipmentId() + " and related order status updated successfully.");

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            throw new DAOException("updateShipment failed", e);
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DAOException("Failed to update shipment", e);
                }
            }
        }
    }

    @Override
    public void deleteShipment(int shipmentId) throws DAOException {
        final String queryDelete = "DELETE FROM shipments WHERE shipment_id = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement= connection.prepareStatement(queryDelete)) {

            preparedStatement.setInt(1, shipmentId);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Shipment deleted.");
            } else {
                System.out.println("Shipment not found.");
            }

        } catch (SQLException e) {
            throw new DAOException("deleteShipment failed", e);
        }
    }
}
