package com.nhom13.bidding.controller;

import com.nhom13.bidding.model.SessionManager;
// import com.nhom13.bidding.manager.SceneManager; // Tạm thời comment dòng này lại để tránh báo đỏ
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
            System.out.println("Chuyển hướng sang màn hình Home...");
            // SceneManager.getInstance().switchScene("Home.fxml"); // Tạm thời comment lại
        } else {
            showAlert("Thất bại", "Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}