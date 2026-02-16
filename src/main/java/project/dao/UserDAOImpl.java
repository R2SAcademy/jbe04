package project.dao;

import project.entity.User;
import project.exception.DAOException;
import project.util.JDBCUtil;
import java.sql.*;
import java.util.Optional;


public class UserDAOImpl implements UserDAO {

    @Override
    public void insert(User user) throws DAOException {
        String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertSql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection()) {

            //Check username
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setString(1, user.getUsername());
                ResultSet rs = psCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new DAOException("Username already exists", null);
                }
            }

            //không trung -> insert user
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                int index = 1;
                psInsert.setString(index++, user.getUsername());
                psInsert.setString(index++, user.getPasswordHash());
                psInsert.setString(index++, user.getRole());

                psInsert.executeUpdate();
            }

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

            //kiem tra du lieu
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
