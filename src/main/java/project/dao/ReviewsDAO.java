package project.dao;

import project.entity.Reviews;
import project.exception.DAOException;

import java.util.List;

public interface ReviewsDAO {
    boolean create(Reviews review) throws DAOException;
    List<Reviews> findByProductId(int productId) throws DAOException;
    boolean updateByIdAndCustomer(Reviews review) throws DAOException;
    boolean deleteByReviewId(int reviewId) throws DAOException;
    boolean deleteByCustomerAndProduct(int customerId, int productId) throws DAOException;
}