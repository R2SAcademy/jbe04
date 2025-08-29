package project.dao;

import project.entity.User;
import project.exception.DAOException;

public interface UserDAO {
    void insert(User user) throws DAOException;
}
