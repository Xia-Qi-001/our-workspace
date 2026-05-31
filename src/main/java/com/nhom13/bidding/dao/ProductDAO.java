package com.nhom13.bidding.dao;

import com.nhom13.bidding.core.DatabaseConnection;
import com.nhom13.bidding.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE status = 'Đang đấu giá' AND end_time > ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, java.time.LocalDateTime.now());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setStartPrice(rs.getDouble("start_price"));
                    p.setStepPrice(rs.getDouble("step_price"));
                    p.setCurrentPrice(rs.getDouble("current_price"));
                    p.setSellerId(rs.getInt("seller_id"));
                    p.setStatus(rs.getString("status"));
                    p.setImagePath(rs.getString("image_path"));

                    // Nạp ID người thắng
                    p.setCurrentWinnerId(rs.getInt("current_winner_id"));

                    java.sql.Timestamp ts = rs.getTimestamp("end_time");
                    if (ts != null) {
                        p.setEndTime(ts.toLocalDateTime());
                    }

                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO product (name, start_price, step_price, current_price, seller_id, end_time, status, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getStartPrice());
            pstmt.setDouble(3, product.getStepPrice());
            pstmt.setDouble(4, product.getStartPrice());
            pstmt.setInt(5, product.getSellerId());
            pstmt.setObject(6, product.getEndTime());
            pstmt.setString(7, product.getStatus());
            pstmt.setString(8, product.getImagePath());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setStartPrice(rs.getDouble("start_price"));
                    p.setStepPrice(rs.getDouble("step_price"));
                    p.setCurrentPrice(rs.getDouble("current_price"));
                    p.setSellerId(rs.getInt("seller_id"));
                    p.setImagePath(rs.getString("image_path"));

                    // Nạp ID người thắng
                    p.setCurrentWinnerId(rs.getInt("current_winner_id"));

                    java.sql.Timestamp ts = rs.getTimestamp("end_time");
                    if (ts != null) {
                        p.setEndTime(ts.toLocalDateTime());
                    }
                    p.setStatus(rs.getString("status"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getLatestPrice(int productId) {
        String sql = "SELECT current_price FROM product WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getDouble("current_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateBidPrice(int productId, double newPrice, int userId) {
        String sql = "UPDATE product SET current_price = ?, current_winner_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, productId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductStatus(int productId, String newStatus) {
        String sql = "UPDATE product SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, productId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> searchActiveProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE status = 'Đang đấu giá' AND end_time > ? AND name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, java.time.LocalDateTime.now());
            pstmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setCurrentPrice(rs.getDouble("current_price"));
                    p.setImagePath(rs.getString("image_path"));
                    p.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());

                    // Nạp ID người thắng
                    p.setCurrentWinnerId(rs.getInt("current_winner_id"));

                    // --- DÒNG FIX BUG CHÍ MẠNG ---
                    p.setSellerId(rs.getInt("seller_id"));
                    p.setStepPrice(rs.getDouble("step_price"));
                    p.setStartPrice(rs.getDouble("start_price"));

                    list.add(p);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> getPendingDeliveries(int buyerId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE current_winner_id = ? AND status = 'Chờ giao hàng'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, buyerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setCurrentPrice(rs.getDouble("current_price"));
                    p.setSellerId(rs.getInt("seller_id"));
                    list.add(p);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}