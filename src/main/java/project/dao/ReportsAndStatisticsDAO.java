package project.dao;

import project.exception.DAOException;

public interface ReportsAndStatisticsDAO {
    void StockLevelsByStore(Integer storedId, Integer lowStockThreshold) throws DAOException;
    void SalesByCategory(Integer categoryId) throws DAOException;
    void BestSellingProductByQuantity(int topN) throws DAOException;
    void BestSellingProductByRevenue(int topN) throws DAOException;
    void TopCustomer(int month, int year) throws DAOException;
    void DailyMonthlyRevenue(int month, int year) throws DAOException;
}
