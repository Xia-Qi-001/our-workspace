package com.nhom13.bidding.core;

import com.nhom13.bidding.model.User;
import com.nhom13.bidding.model.Role;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private List<User> localDatabase; // Kho lưu tài khoản tạm thời bằng List

    private SessionManager() {
        localDatabase = new ArrayList<>();
        // Nạp sẵn 3 tài khoản mẫu hệ thống
        localDatabase.add(new User(1, "admin", "admin123", "admin@uet.vnu.edu.vn", Role.ADMIN, 50000000, true));
        localDatabase.add(new User(2, "hoang", "123", "hoang@gmail.com", Role.USER, 1500000, true));
        localDatabase.add(new User(3, "huy", "123", "huy@gmail.com", Role.USER, 2000000, true));
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Duyệt List để check đăng nhập
    public boolean login(String username, String password) {
        for (User u : localDatabase) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                this.currentUser = u;
                return true;
            }
        }
        return false;
    }

    // Thêm hẳn tài khoản mới vào List
    public boolean register(String username, String password, String email) {
        // Chặn trùng tên đăng nhập
        for (User u : localDatabase) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }

        int nextId = localDatabase.size() + 1;
        // Khởi tạo đối tượng User mới và ném vào kho
        User newUser = new User(nextId, username, password, email, Role.USER, 0.0, true);
        localDatabase.add(newUser);
        System.out.println("localDatabase hiện tại có: " + localDatabase.size() + " tài khoản.");
        return true;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}