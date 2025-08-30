package project.dao;

import project.entity.Wishlist;
import project.exception.DAOException;
import java.util.List;

public interface WishlistDAO {
    void insert(Wishlist wishlist) throws DAOException;
    void delete(int wishlistId) throws DAOException;
    Wishlist findById(int wishlistId) throws DAOException;
    List<Wishlist> findAll() throws DAOException;
}
