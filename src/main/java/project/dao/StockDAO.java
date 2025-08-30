package project.dao;

import project.entity.Stock;
import project.exception.DAOException;

import java.util.List;

public interface StockDAO {
    void insert(Stock stock) throws DAOException;
    void update(Stock stock) throws DAOException;
    void delete(int stockId) throws DAOException;
    List<Stock> search (Stock stock) throws DAOException;
}
