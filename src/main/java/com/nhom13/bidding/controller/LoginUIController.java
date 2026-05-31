package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.dao.UserDAO;
import com.nhom13.bidding.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginUIController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    // Khởi tạo DAO để giao tiếp với MySQL
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLoginClick() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            SceneManager.getInstance().showPopup("Lỗi", "Không được để trống tài khoản và mật khẩu!");
            return;
        }

        // Gọi truy vấn thật xuống Aiven thông qua DAO
        User loggedInUser = userDAO.loginUser(username, password);

        if (loggedInUser != null) {
            // Đăng nhập thành công -> Lưu dữ liệu thật vào phiên làm việc (Session)
            SessionManager.getInstance().setCurrentUser(loggedInUser);
            SceneManager.getInstance().switchScene("home.fxml");
        } else {
            // Trả về null nghĩa là không tìm thấy trong DB
            SceneManager.getInstance().showPopup("Lỗi", "Sai tài khoản hoặc mật khẩu!");
        }
    }

    @FXML
    public void handleGoToRegister() {
        SceneManager.getInstance().switchScene("register.fxml");
    }
}