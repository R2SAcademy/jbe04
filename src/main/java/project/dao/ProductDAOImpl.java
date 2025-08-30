package project.dao;

import project.entity.Brand;
import project.entity.Product;
import project.exception.DAOException;
import project.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static project.util.Constants.*;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public void insert(Product product) throws DAOException {
        String sql = "INSERT INTO products (" + PRODUCT_NAME + ", " + CATEGORY_ID + ", " + BRAND_ID + ", " + MODEL_YEAR + ", " + PRICE + ", " + DESCRIPTION + ") VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;

            stmt.setString(index++, product.getProductName());
            stmt.setInt(index++, product.getCategoryId());
            stmt.setInt(index++, product.getBrandId());
            stmt.setInt(index++, product.getModelYear());
            stmt.setFloat(index++, product.getPrice());
            stmt.setString(index++, product.getDescription());

            if (stmt.executeUpdate() > 0) {
                System.out.println("create product successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to insert product.", e);
        }
    }

    @Override
    public void update(Product product) throws DAOException {
        StringBuilder sql = new StringBuilder("UPDATE products SET ");
        List<Object> tempList = new ArrayList<>();

        // Nếu field khác null thì thêm vào SQL và params
        if (product.getProductName() != null) {
            sql.append(PRODUCT_NAME + "=?, ");
            tempList.add(product.getProductName());
        }
        if (product.getCategoryId() != null) {
            sql.append(CATEGORY_ID + "=?, ");
            tempList.add(product.getCategoryId());
        }
        if (product.getBrandId() != null) {
            sql.append(BRAND_ID + "=?, ");
            tempList.add(product.getBrandId());
        }
        if (product.getModelYear() != null) {
            sql.append(MODEL_YEAR + "=?, ");
            tempList.add(product.getModelYear());
        }
        if (product.getPrice() != null) {
            sql.append(PRICE + "=?, ");
            tempList.add(product.getPrice());
        }
        if (product.getDescription() != null) {
            sql.append(DESCRIPTION + "=?, ");
            tempList.add(product.getDescription());
        }

        if (tempList.isEmpty()) {
            System.out.println("Nothing to update!");
            return;
        }

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE product_id=?");
        tempList.add(product.getProductId());

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < tempList.size(); i++) {
                stmt.setObject(i + 1, tempList.get(i));
            }

            if (stmt.executeUpdate() > 0) {
                System.out.println("update product successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to update product.", e);
        }
    }

    @Override
    public void delete(int productId) throws DAOException {
        String sql = "DELETE FROM products WHERE " + PRODUCT_ID + "=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);

            if (stmt.executeUpdate() > 0) {
                System.out.println("delete product successful!");
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to delete product.", e);
        }
    }

    @Override
    public List<Product> search(String keyword, Integer categoryId, Integer brandId, Integer modelYear, Float minPrice, Float maxPrice) throws DAOException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE 1=1";

        List<Object> tempList = new ArrayList<>(); // chứa giá trị để set vào ?

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND name LIKE ?";
            tempList.add("%" + keyword + "%");
        }
        if (categoryId != null) {
            sql += " AND category_id = ?";
            tempList.add(categoryId);
        }
        if (brandId != null) {
            sql += " AND brand_id = ?";
            tempList.add(brandId);
        }
        if (modelYear != null) {
            sql += " AND model_year = ?";
            tempList.add(modelYear);
        }
        if (minPrice != null) {
            sql += " AND price >= ?";
            tempList.add(minPrice);
        }
        if (maxPrice != null) {
            sql += " AND price <= ?";
            tempList.add(maxPrice);
        }

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < tempList.size(); i++) {
                stmt.setObject(i + 1, tempList.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();

                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("name"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setBrandId(rs.getInt("brand_id"));
                    product.setModelYear(rs.getInt("model_year"));
                    product.setPrice(rs.getFloat("price"));
                    product.setDescription(rs.getString("description"));

                    list.add(product);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to get stock list.", e);
        }

        return list;
    }
}
