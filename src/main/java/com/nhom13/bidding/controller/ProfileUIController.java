package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileUIController {

    @FXML private Label lblUsername;
    @FXML private Label lblBalance;
    @FXML private TextField txtDepositAmount;

    @FXML
    public void initialize() {
        User user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            lblUsername.setText(user.getUsername());
            lblBalance.setText(String.format("%,.0f VNĐ", user.getBalance()));
        }
    }

    @FXML
    public void handleDepositClick() {
        try {
            double amount = Double.parseDouble(txtDepositAmount.getText().trim());
            User user = SessionManager.getInstance().getCurrentUser();
            if (user != null && amount > 0) {
                user.refundMoney(amount); // Gọi hàm nạp tiền Model
                lblBalance.setText(String.format("%,.0f VNĐ", user.getBalance()));
                txtDepositAmount.clear();
                SceneManager.getInstance().showPopup("Thành công", "Nạp tiền thành công!");
            }
        } catch (NumberFormatException e) {
            SceneManager.getInstance().showPopup("Lỗi", "Số tiền nạp không hợp lệ!");
        }
    }

    // Đã thêm: Hàm quay lại Chợ cho nút bấm
    @FXML
    public void handleBackToHome() {
        SceneManager.getInstance().switchScene("home.fxml");
    }
}