package com.nhom13.bidding.core;

import com.nhom13.bidding.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser; // Chỉ lưu duy nhất 1 User đang online

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }
}