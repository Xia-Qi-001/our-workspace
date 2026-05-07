package com.nhom13.bidding;

/**
 * User - Model đơn giản đại diện cho người dùng trong hệ thống đấu giá.
 *
 * Lớp này lưu trữ thông tin cơ bản của người dùng (ID và tên đăng nhập),
 * được sử dụng bởi SessionManager để quản lý phiên đăng nhập.
 */
public class User {
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
