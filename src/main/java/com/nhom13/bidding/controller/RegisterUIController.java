package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.dao.UserDAO;
import com.nhom13.bidding.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterUIController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtEmail;

    // Khởi tạo đối tượng DAO để giao tiếp với MySQL
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleRegisterClick() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();

        // 1. Kiểm tra dữ liệu đầu vào (Validation)
        if (username.isEmpty() || password.isEmpty()) {
            SceneManager.getInstance().showPopup("Lỗi", "Không được để trống tài khoản và mật khẩu!");
            return;
        }

        // 2. Đóng gói dữ liệu vào Model
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Lưu ý: Tạm thời lưu text thô, thực tế sẽ phải băm (hash) mật khẩu
        newUser.setEmail(email);
        newUser.setBalance(100000.0); // Tặng 1 trăm nghìn VNĐ làm phúc lợi lúc tạo nick

        // 3. Đẩy xuống MySQL thông qua DAO
        boolean isSuccess = userDAO.registerUser(newUser);

        // 4. Xử lý kết quả trả về
        if (isSuccess) {
            SceneManager.getInstance().showPopup("Thành công", "Đăng ký tài khoản thành công!");
            SceneManager.getInstance().switchScene("login.fxml");
        } else {
            SceneManager.getInstance().showPopup("Lỗi", "Tài khoản đã tồn tại hoặc lỗi mạng!");
        }
    }

    @FXML
    public void handleBackToLogin() {
        SceneManager.getInstance().switchScene("login.fxml");
    }
}