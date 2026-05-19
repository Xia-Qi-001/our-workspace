package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginUIController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void handleLoginClick() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        boolean isSuccess = SessionManager.getInstance().login(username, password);

        if (isSuccess) {
            System.out.println("Đăng nhập thành công! Chuyển hướng sang màn hình Home...");
            // Lệnh gọi SceneManager từ thư mục core để chuyển trang
            SceneManager.getInstance().switchScene("home.fxml");
        } else {
            showAlert("Thất bại", "Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại!");
        }
    }

    // Gắn hàm chuyển sang trang Đăng Ký ở đây
    @FXML
    public void handleGoToRegister() {
        SceneManager.getInstance().switchScene("register.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}