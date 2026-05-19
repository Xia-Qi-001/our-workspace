package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.ProductManager;
import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;

public class SellerUIController {

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtStartPrice;

    @FXML
    private void handleCreateAuction() {
        String productName = txtProductName.getText().trim();
        String startPriceText = txtStartPrice.getText().trim();

        if (productName.isEmpty() || startPriceText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đủ tên và giá khởi điểm!");
            return;
        }

        double startPrice = Double.parseDouble(startPriceText);
        int sellerId = SessionManager.getInstance().getCurrentUser().getId();
        int newId = ProductManager.getInstance().generateNextId();
        double stepPrice = startPrice * 0.1;
        LocalDateTime endTime = LocalDateTime.now().plusHours(24);

        // Tạo đồ án mới
        Product newProduct = new Product(newId, productName, startPrice, stepPrice, sellerId, endTime);

        // NHÉT VÀO KHO ẢO Ở ĐÂY!
        ProductManager.getInstance().addProduct(newProduct);

        showAlert("Thành công", "Đăng bán sản phẩm thành công!");

        // Chuyển thẳng về Home để xem thành quả
        SceneManager.getInstance().switchScene("home.fxml");
    }

    @FXML
    private void handleBackToHome() {
        SceneManager.getInstance().switchScene("home.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}