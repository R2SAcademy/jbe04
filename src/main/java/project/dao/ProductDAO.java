package project.dao;
import project.entity.Product;
import project.exception.DAOException;

import java.util.List;

public interface ProductDAO {
    void insert(Product product) throws DAOException;
    void update(Product product) throws DAOException;
    void delete(int productId) throws DAOException;
    List<Product> search(String keyword, Integer categoryId, Integer brandId, Integer modelYear, Float minPrice, Float maxPrice) throws DAOException;
}
