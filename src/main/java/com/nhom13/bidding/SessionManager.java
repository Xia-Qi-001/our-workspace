package com.nhom13.bidding;

/**
 * SessionManager - Singleton quản lý phiên đăng nhập của người dùng hiện tại.
 *
 * Cung cấp truy cập toàn cục đến thông tin User đang đăng nhập,
 * được sử dụng bởi các Controller để lấy thông tin như sellerId, bidderId.
 *
 * Cách sử dụng:
 *   SessionManager.getInstance().setCurrentUser(new User(1, "admin"));
 *   User user = SessionManager.getInstance().getCurrentUser();
 */
public class SessionManager {

    // --- Singleton Pattern ---
    private static SessionManager instance;

    private User currentUser;

    private SessionManager() {
        // Private constructor ngăn khởi tạo từ bên ngoài
    }

    /**
     * Lấy instance duy nhất của SessionManager.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // --- Quản lý User hiện tại ---

    /**
     * Thiết lập người dùng hiện tại khi đăng nhập thành công.
     *
     * @param user Đối tượng User đã đăng nhập
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Lấy thông tin người dùng hiện tại.
     *
     * @return User đang đăng nhập, hoặc null nếu chưa đăng nhập
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Đăng xuất: Xóa thông tin người dùng khỏi phiên.
     */
    public void logout() {
        this.currentUser = null;
    }
}
