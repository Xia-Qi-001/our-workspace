package com.nhom13.bidding.dao;

import com.nhom13.bidding.core.DatabaseConnection;
import com.nhom13.bidding.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // 1. Chức năng Đăng ký: Ghi user mới vào MySQL
    public boolean registerUser(User user) {
        String sql = "INSERT INTO user (username, password, email, balance) VALUES (?, ?, ?, ?)";

        // Thêm GENERATED_KEYS để MySQL trả về ID vừa tự tăng
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setDouble(4, user.getBalance());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Lấy ID vừa sinh ra nạp ngược vào đối tượng User trên RAM
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Chức năng Đăng nhập: Kiểm tra tài khoản trong MySQL
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    // BỔ SUNG DÒNG NÀY ĐỂ LẤY ID TỪ MYSQL
                    user.setId(rs.getInt("id"));

                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setBalance(rs.getDouble("balance"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Sai tài khoản/mật khẩu hoặc lỗi kết nối
    }


    public boolean updateBalance(int userId, double amount) {
        // Thêm chốt chặn: Chỉ trừ khi balance hiện tại >= số tiền cần trừ
        String sql = "UPDATE user SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userId);
            pstmt.setDouble(3, amount); // Tham số thứ 3 cho điều kiện kiểm tra

            // Nếu không đủ tiền, executeUpdate() sẽ trả về 0 -> return false
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm cộng tiền cho người bán khi giao dịch hoàn tất
    public boolean addBalance(int userId, double amount) {
        String sql = "UPDATE user SET balance = balance + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}