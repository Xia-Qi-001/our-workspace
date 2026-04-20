package com.nhom13.bidding.view;

/**
 * Lớp Main này đóng vai trò là "vỏ bọc" để khởi chạy ứng dụng.
 * Việc không kế thừa Application ở đây giúp tránh lỗi thiếu runtime components
 * khi chạy trực tiếp trên một số phiên bản JDK mới.
 */
public class Main {
    public static void main(String[] args) {
        // Chuyển quyền điều khiển sang cho lớp MainApp thực sự
        MainApp.main(args);
    }
}