package project.dao;

import project.entity.Order;
import project.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Order insert(Order order) throws DAOException;
    boolean update(Order order) throws DAOException;
    boolean delete(int orderId) throws DAOException;

    Optional<Order> findById(int orderId) throws DAOException;
    List<Order> findAll() throws DAOException;
    List<Order> findByCustomerId(int customerId) throws DAOException;
}
