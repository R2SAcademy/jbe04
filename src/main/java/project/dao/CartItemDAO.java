package project.dao;

import project.entity.CartItem;
import project.exception.DAOException;

import java.util.List;

public interface CartItemDAO {
    void addOrUpdateCartItem(int cartId, int productId, int quantity) throws DAOException;
    List<CartItem> findByCartId(int cartId) throws DAOException;
    void updateQuantity(int cartItemId, int quantity) throws DAOException;
    void deleteItem(int cartId, int productId) throws DAOException;
    void clearCart(int cartId) throws DAOException;
}
