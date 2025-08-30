package project.dao;

import project.entity.Discount;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiscountDAOImpl implements DiscountDAO {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(Discount discount) throws DAOException {
        String sql = "INSERT INTO discounts (discount_name, percentage, start_date, end_date) VALUES (?, ?, ?, ?)";
        boolean success = false;

        while (!success) {
            try (Connection conn = JDBCUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, discount.getDiscountName());
                stmt.setDouble(2, discount.getPercentage());
                stmt.setDate(3, Date.valueOf(discount.getStartDate()));
                stmt.setDate(4, Date.valueOf(discount.getEndDate()));
                stmt.executeUpdate();
                System.out.println("Discount added successfully!");
                success = true;

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Constraint error: " + e.getMessage());
                System.out.println("Please enter the discount information again.");

                System.out.print("Enter discount name: ");
                String name = scanner.nextLine();
                System.out.print("Enter discount percentage: ");
                double percent = scanner.nextDouble();
                scanner.nextLine(); // clear buffer
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String start = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String end = scanner.nextLine();

                discount.setDiscountName(name);
                discount.setPercentage(percent);
                discount.setStartDate(java.time.LocalDate.parse(start));
                discount.setEndDate(java.time.LocalDate.parse(end));

            } catch (SQLException e) {
                throw new DAOException("Failed to insert discount", e);
            }
        }
    }

    @Override
    public void update(Discount discount) throws DAOException {
        String sql = "UPDATE discounts SET discount_name=?, percentage=?, start_date=?, end_date=? WHERE discount_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discount.getDiscountName());
            stmt.setDouble(2, discount.getPercentage());
            stmt.setDate(3, Date.valueOf(discount.getStartDate()));
            stmt.setDate(4, Date.valueOf(discount.getEndDate()));
            stmt.setInt(5, discount.getDiscountId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Discount updated successfully!");
            } else {
                System.out.println("Discount with ID " + discount.getDiscountId() + " not found.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to update discount", e);
        }
    }

    @Override
    public void delete(int discountId) throws DAOException {
        String sql = "DELETE FROM discounts WHERE discount_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, discountId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Discount deleted successfully!");
            } else {
                System.out.println("Discount with ID " + discountId + " not found.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to delete discount", e);
        }
    }

    @Override
    public Discount findById(int discountId) throws DAOException {
        String sql = "SELECT * FROM discounts WHERE discount_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, discountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Discount(
                        rs.getInt("discount_id"),
                        rs.getString("discount_name"),
                        rs.getDouble("percentage"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                );
            } else {
                System.out.println("Discount with ID " + discountId + " not found.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to find discount by id", e);
        }
        return null;
    }

    @Override
    public List<Discount> findAll() throws DAOException {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT * FROM discounts";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Discount(
                        rs.getInt("discount_id"),
                        rs.getString("discount_name"),
                        rs.getDouble("percentage"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
            if (list.isEmpty()) {
                System.out.println("Discount list is empty.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to get discount list", e);
        }
        return list;
    }
}
