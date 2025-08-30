package project.dao;

import project.entity.Wishlist;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WishlistDAOImpl implements WishlistDAO {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void insert(Wishlist wishlist) throws DAOException {
        String sql = "INSERT INTO wishlists (customer_id, product_id) VALUES (?, ?)";
        boolean success = false;

        while (!success) {
            try (Connection conn = JDBCUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, wishlist.getCustomerId());
                stmt.setInt(2, wishlist.getProductId());
                stmt.executeUpdate();
                System.out.println("Wishlist added successfully!");
                success = true;

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Constraint error: " + e.getMessage());
                System.out.println("Please enter valid customer_id and product_id.");
                System.out.print("Enter customer_id: ");
                int customerId = scanner.nextInt();
                System.out.print("Enter product_id: ");
                int productId = scanner.nextInt();
                wishlist.setCustomerId(customerId);
                wishlist.setProductId(productId);

            } catch (SQLException e) {
                throw new DAOException("Failed to insert wishlist", e);
            }
        }
    }

    @Override
    public void delete(int wishlistId) throws DAOException {
        String sql = "DELETE FROM wishlists WHERE wishlist_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Wishlist item deleted successfully.");
            } else {
                System.out.println("Wishlist with ID " + wishlistId + " not found.");
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to delete wishlist", e);
        }
    }

    @Override
    public Wishlist findById(int wishlistId) throws DAOException {
        String sql = "SELECT * FROM wishlists WHERE wishlist_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Wishlist(
                        rs.getInt("wishlist_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("product_id"));
            } else {
                System.out.println("Wishlist with ID " + wishlistId + " not found.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to find wishlist by id", e);
        }
        return null;
    }

    @Override
    public List<Wishlist> findAll() throws DAOException {
        List<Wishlist> list = new ArrayList<>();
        String sql = "SELECT * FROM wishlists";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Wishlist(
                        rs.getInt("wishlist_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("product_id")));
            }
            if (list.isEmpty()) {
                System.out.println("Wishlist list is empty.");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to get all wishlists", e);
        }
        return list;
    }
}
