package project.dao;

import project.entity.Product;
import project.entity.Stock;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static project.util.Constants.*;

public class StockDAOImpl implements StockDAO {
    @Override
    public void insert(Stock stock) throws DAOException {
        String sql = "INSERT INTO stocks ("+STORE_ID+", "+PRODUCT_ID+", "+QUANTITY+") VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, stock.getStoreId());
            stmt.setInt(index++, stock.getProductId());
            stmt.setInt(index++, stock.getQuantity());
            stmt.executeUpdate();

            if (stmt.executeUpdate() > 0) {
                System.out.println("create stock successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to insert stock.", e);
        }
    }

    @Override
    public void update(Stock stock) throws DAOException {
        String sql = "UPDATE stocks SET "+STORE_ID+"=?, "+PRODUCT_ID+"=?, "+QUANTITY+"=? WHERE "+STOCK_ID+"=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, stock.getStoreId());
            stmt.setInt(index++, stock.getProductId());
            stmt.setInt(index++, stock.getQuantity());
            stmt.setInt(index++, stock.getStockId());

            if (stmt.executeUpdate() > 0) {
                System.out.println("update stock successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to update stock.", e);
        }
    }

    @Override
    public void delete(int stockId) throws DAOException {
        String sql = "DELETE FROM stocks WHERE "+STOCK_ID+"=?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockId);

            if (stmt.executeUpdate() > 0) {
                System.out.println("delete stock successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to delete stock.", e);
        }
    }


    @Override
    public List<Stock> search(Stock tempStock) throws DAOException {
        List<Stock> list = new ArrayList<>();

//        String sql = "SELECT s.stock_id, s.store_id, st.store_name," +
//                " s.product_id, p.product_name, s.quantity" +
//                " FROM stocks s" +
//                " JOIN stores st ON s.store_id = st.store_id" +
//                " JOIN products p ON s.product_id = p.product_id" +
//                " WHERE 1=1";

        String sql  = "SELECT * FROM stocks WHERE 1=1";
        List<Object> tempList = new ArrayList<>();

        if (tempStock.getStoreId() != null) {
            sql += " AND store_id = ?";
            tempList.add(tempStock.getStoreId());
        }
        if (tempStock.getProductId() != null) {
            sql += " AND product_id = ?";
            tempList.add(tempStock.getProductId());
        }
        if (tempStock.getQuantity() != null) {
            sql += " AND quantity <= ?";
            tempList.add(tempStock.getQuantity());
        }
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            for (int i = 0; i < tempList.size(); i++) {
                stmt.setObject(i + 1, tempList.get(i));
            }

            while (rs.next()) {
                Stock stock = new Stock();

                stock.setStockId(rs.getInt("stock_id"));
                stock.setStoreId(rs.getInt("store_id"));
                stock.setProductId(rs.getInt("product_id"));
                stock.setQuantity(rs.getInt("quantity"));

                list.add(stock);
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to get stock list.", e);
        }
        return list;
    }
}


