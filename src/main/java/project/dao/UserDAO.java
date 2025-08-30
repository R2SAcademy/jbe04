package project.dao;

import project.entity.User;
import project.exception.DAOException;

import java.util.Optional;

public interface UserDAO {
    void insert(User user) throws DAOException;
    Optional<User> findByUsername(String username) throws DAOException;
}
