package project.dao;

import project.entity.User;
import project.exception.DAOException;
import project.util.JDBCUtil;
import java.sql.*;


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
}
