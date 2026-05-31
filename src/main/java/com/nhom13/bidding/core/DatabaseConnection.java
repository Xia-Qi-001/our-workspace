package com.nhom13.bidding.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Thông số cấu hình từ cấu hình IntelliJ đã chạy thành công
    private static final String HOST = "mysql-auction-db-onlineauctionsystem13.e.aivencloud.com";
    private static final String PORT = "16405";
    private static final String DATABASE = "defaultdb";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_SKktHOY92OABQIxRDh_";

    // Chuỗi URL bắt buộc có mã hóa mã nguồn SSL cho Aiven
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?sslMode=REQUIRED";

    public static Connection getConnection() throws SQLException {
        try {
            // Nạp driver MySQL vào bộ nhớ
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy Driver MySQL Connector!", e);
        }
    }
}