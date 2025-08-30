package project.dao;

import project.entity.Reviews;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDAOImpl implements ReviewsDAO {

    @Override
    public boolean create(Reviews review) throws DAOException {
        final String insert = "INSERT INTO reviews (customer_id, product_id, rating, comment, review_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        int index = 1;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setInt(index++, review.getCustomerId());
            ps.setInt(index++, review.getProductId());
            ps.setInt(index++, review.getRating());
            ps.setString(index++, review.getComment());

            LocalDateTime reviewDateTime;
            if (review.getReviewDate() != null) {
                reviewDateTime = review.getReviewDate();
            } else {
                reviewDateTime = LocalDateTime.now();
            }
            ps.setTimestamp(index++, Timestamp.valueOf(reviewDateTime));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException(" Create review failed", e);
        }
    }

    @Override
    public List<Reviews> findByProductId(int productId) throws DAOException {
        final String sql = "SELECT review_id, customer_id, product_id, rating, comment, review_date " +
                "FROM reviews WHERE product_id=? ORDER BY review_date DESC";
        List<Reviews> list = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reviews r = new Reviews();
                    r.setReviewId(rs.getInt("review_id"));
                    r.setCustomerId(rs.getInt("customer_id"));
                    r.setProductId(rs.getInt("product_id"));
                    r.setRating(rs.getInt("rating"));
                    r.setComment(rs.getString("comment"));
                    Timestamp ts = rs.getTimestamp("review_date");
                    r.setReviewDate(ts != null ? ts.toLocalDateTime() : null);
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("findByProductId failed", e);
        }
        return list;
    }

    @Override
    public boolean updateByIdAndCustomer(Reviews review) throws DAOException {
        final String sql = "UPDATE reviews SET rating=?, comment=? WHERE review_id=? AND customer_id=?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setInt(3, review.getReviewId());
            ps.setInt(4, review.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("updateByIdAndCustomer failed", e);
        }
    }

    @Override
    public boolean deleteByReviewId(int reviewId) throws DAOException {
        final String sql = "DELETE FROM reviews WHERE review_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reviewId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("deleteByReviewId failed", e);
        }
    }

    @Override
    public boolean deleteByCustomerAndProduct(int customerId, int productId) throws DAOException {
        final String sql = "DELETE FROM reviews WHERE customer_id = ? AND product_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("deleteByCustomerAndProduct failed", e);
        }
    }


}

