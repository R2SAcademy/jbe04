package project.dao;

import project.exception.DAOException;
import project.util.Constants;
import project.util.JDBCUtil;

import java.sql.*;

public class ReportsAndStatisticsImpl implements ReportsAndStatisticsDAO{
    @Override
    public void StockLevelsByStore(Integer storedId, Integer lowStockThreshold) throws DAOException {
        String function = Constants.CALL + " " + Constants.GETSTOCKLEVELS + "(?, ?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            if(storedId == null) {
                stmt.setNull(index++, Types.INTEGER);
            } else {
                stmt.setInt(index++, storedId);
            }

            if(lowStockThreshold == null) {
                stmt.setNull(index++, Types.INTEGER);
            } else {
                stmt.setInt(index++, lowStockThreshold);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int storeId =  rs.getInt("store_id");
                String storeName = rs.getString("store_name");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");

                System.out.println("StoreId: " + storeId + " StoreName: " + storeName + " ProductId: " + productId + " ProductName: " + productName + " Quantity: " + quantity);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }

    @Override
    public void SalesByCategory(Integer categoryId) throws DAOException {
        String function = Constants.CALL + " " + Constants.SALESBYCATEGORYQUERY + "(?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            if(categoryId == null) {
                stmt.setNull(index++, Types.INTEGER);
            } else {
                stmt.setInt(index++, categoryId);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int category_id =  rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                double revenuePerCategory = rs.getInt("revenuePerCategory");
                int totalSelling = rs.getInt("total_selling");
                String bestSelling = rs.getString("best_selling_category_name");

                System.out.println("CategoryId: " + category_id + " CategoryName: " + category_name + " Revenue: " + revenuePerCategory + " TotalSelling: " + totalSelling + " CategoryBestSelling: " + bestSelling);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }

    @Override
    public void BestSellingProductByQuantity(int topN) throws DAOException {
        String function = Constants.CALL + " " + Constants.BESTSELLINGPRODUCTBYQUANTITYQUERY + "(?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            stmt.setInt(index++, topN);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int rank =  rs.getInt("rnk");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("name");
                int quantity = rs.getInt("total_quantity");

                System.out.println("Rank: " + rank + " ProductId: " + productId + " ProductName: " + productName + " TotalQuantity: " + quantity);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }

    @Override
    public void BestSellingProductByRevenue(int topN) throws DAOException {
        String function = Constants.CALL + " " + Constants.BESTSELLINGPRODUCTBYREVENUEQUERY + "(?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            stmt.setInt(index++, topN);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int rank =  rs.getInt("rnk");
                int productId = rs.getInt("product_id");
                String productName = rs.getString("name");
                int totalRevenue = rs.getInt("total_revenue");

                System.out.println("Rank: " + rank + " ProductId: " + productId + " ProductName: " + productName + " TotalRevenue: " + totalRevenue);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }

    @Override
    public void TopCustomer(int month, int year) throws DAOException {
        String function =  Constants.CALL + " " + Constants.TOPCUSTOMERQUERY + "(?, ?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            stmt.setInt(index++, month);
            stmt.setInt(index++, year);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                int orderCounts = rs.getInt("order counts");
                double totalSpent = rs.getDouble("total amount spent");

                System.out.println("Name: " + name + " Order Counts: " + orderCounts + " Total Amount Spent: " + totalSpent);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }

    @Override
    public void DailyMonthlyRevenue(int month, int year) throws DAOException {
        String function = Constants.CALL + " " + Constants.REVENUEREPORTDAILYMONTHLY + "(?, ?)";

        int index = 1;

        try(Connection conn = JDBCUtil.getConnection();
            CallableStatement stmt = conn.prepareCall(function);) {

            stmt.setInt(index++, month);
            stmt.setInt(index++, year);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String order_date =  rs.getString("order_day");
                double revenuePerDay = rs.getDouble("daily_revenue");

                System.out.println("Order day: " + order_date + " Revenue/Day: " + revenuePerDay);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed call function", e);
        }
    }
}
