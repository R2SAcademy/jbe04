package project.dao;

import project.entity.Discount;
import project.exception.DAOException;
import java.util.List;

public interface DiscountDAO {
    void insert(Discount discount) throws DAOException;
    void update(Discount discount) throws DAOException;
    void delete(int discountId) throws DAOException;
    Discount findById(int discountId) throws DAOException;
    List<Discount> findAll() throws DAOException;
}
