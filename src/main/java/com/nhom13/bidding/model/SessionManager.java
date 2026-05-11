package com.nhom13.bidding.model;
import com.nhom13.bidding.service.UserService;
public class SessionManager {
    // 1. Từ khóa 'volatile' ép máy tính đồng bộ hóa bộ nhớ ngay lập tức giữa các luồng
    private static volatile SessionManager instance;
    private User currentUser;
    // Gọi UserService vào để làm nhiệm vụ kiểm tra database & hash password
    private UserService userService;
    // 2. Constructor: Khởi tạo UserService ngay khi SessionManager được sinh ra lần đầu
    private SessionManager() {
        this.userService = new UserService();
    }
    // 3. NÂNG CẤP: Chống lỗi Đa luồng bằng kỹ thuật "Double-Checked Locking"
    public static SessionManager getInstance() {
        if (instance == null) {
            // Nếu có 2 luồng cùng lao vào 1 lúc, từ khóa synchronized sẽ bắt 1 luồng phải đứng đợi
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }
    public boolean login(String username, String rawPassword) {
        try {
            // Gọi hàm login bên UserService (hàm này đã có logic băm passwordHash và check database)
            User user = userService.login(username, rawPassword);
            if (user != null) {
                this.currentUser = user; // Đăng nhập thành công, lưu thông tin ông này lại
                System.out.println("Đăng nhập thành công! Xin chào: " + user.getUsername());
                return true;
            }
            return false;
        } catch (Exception e) {
            // Hứng trọn vẹn các lỗi như "Tài khoản bị khóa", "Sai mật khẩu"... từ UserService
            System.err.println("Lỗi đăng nhập: " + e.getMessage());
            return false;
        }
    }
    public void logout() {
        this.currentUser = null;
        System.out.println("Đã đăng xuất thành công!");
    }
    public User getCurrentUser() {
        return currentUser;
    }
}