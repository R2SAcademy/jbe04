package project.dao;

import project.entity.User;
import project.exception.DAOException;
import project.util.JDBCUtil;
import java.sql.*;
import java.util.Optional;


public class UserDAOImpl implements UserDAO {

    @Override
    public void insert(User user) throws DAOException {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index =1;
            ps.setString(index++, user.getUsername());
            ps.setString(index++, user.getPasswordHash());
            ps.setString(index++, user.getRole());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Failed to insert user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws DAOException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index =1;
            ps.setString(index, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                );

                return Optional.of(user);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DAOException("Failed to find user by username", e);
        }
    }

}
