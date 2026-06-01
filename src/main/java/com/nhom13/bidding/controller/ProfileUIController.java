package com.nhom13.bidding.controller;

import com.nhom13.bidding.dao.UserDAO;
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

    private UserDAO userDAO = new UserDAO();
    @FXML
    public void handleDepositClick() {
        try {
            double amount = Double.parseDouble(txtDepositAmount.getText().trim());
            User user = SessionManager.getInstance().getCurrentUser();

            if (user != null && amount > 0) {
                //Phải nạp tiền dưới Database trước!
                boolean isDbSuccess = userDAO.addBalance(user.getId(), amount);

                if (isDbSuccess) {
                    user.refundMoney(amount); // DB gọi thành công thì mới cộng tiền trên RAM
                    lblBalance.setText(String.format("%,.0f VNĐ", user.getBalance()));
                    txtDepositAmount.clear();
                    SceneManager.getInstance().showPopup("Thành công", "Nạp tiền thành công!");
                } else {
                    SceneManager.getInstance().showPopup("Lỗi CSDL", "Đường truyền lỗi, chưa thể nạp tiền!");
                }
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
