// CartDAO.java
package project.dao;

import project.entity.Cart;
import project.entity.CartItem;
import project.exception.DAOException;

import java.util.List;

public interface CartDAO {
    Cart getOrCreateCart(int userId) throws DAOException;

    void addProductToCart(int userId, int productId, int quantity) throws DAOException;

    List<CartItem> getCartItems(int userId) throws DAOException;

    void updateCartItem(int userId, int productId, int quantity) throws DAOException;

    void removeCartItem(int userId, int productId) throws DAOException;

    void clearCart(int userId) throws DAOException;
}
