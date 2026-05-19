package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.core.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class RegisterUIController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    public void handleRegisterClick() {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirm = txtConfirmPassword.getText();

        // 1. Kiểm tra bỏ trống
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin bắt buộc!");
            return;
        }

        // 2. Kiểm tra khớp mật khẩu
        if (!password.equals(confirm)) {
            showAlert("Lỗi", "Mật khẩu nhập lại không khớp!");
            return;
        }

        // 3. Gọi logic đăng ký (Giả định SessionManager có hàm register)
        // Lưu ý: Cậu có thể điều chỉnh hàm này tùy theo cách Hoàng viết Service
        boolean isSuccess = SessionManager.getInstance().register(username, password, email);

        if (isSuccess) {
            showInfo("Thành công", "Đăng ký tài khoản thành công! Quay lại đăng nhập.");
            // SceneManager.getInstance().switchScene("login.fxml");
        } else {
            showAlert("Thất bại", "Tên đăng nhập hoặc Email đã tồn tại trên hệ thống!");
        }
    }

    @FXML
    public void handleBackToLogin() {
        SceneManager.getInstance().switchScene("login.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}