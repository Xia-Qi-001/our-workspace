package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.LiveAuctionController;
import com.nhom13.bidding.core.ProductManager;
import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuctionController {

    @FXML private Label lblProductName;
    @FXML private Label lblCurrentPrice;
    private Product currentProduct;
    private LiveAuctionController auctionCore;

    @FXML
    private TextField txtBidAmount;

    @FXML
    public void initialize() {
        // 1. Đọc ID trung chuyển từ kho của Huy
        int selectedId = ProductManager.getInstance().getSelectedProductId();
        currentProduct = ProductManager.getInstance().getProductById(selectedId);

        if (currentProduct != null) {
            // 2. Đổ dữ liệu lên giao diện phòng đấu giá
            lblProductName.setText(currentProduct.getName());
            lblCurrentPrice.setText(String.format("%,.0f VNĐ", currentProduct.getCurrentPrice()));

            // 3. Khởi chạy bộ đếm ngược đa luồng của Tech Lead cho riêng món đồ này
            auctionCore = new LiveAuctionController(currentProduct);
            auctionCore.startAuctionTimer();
        }
    }

    @FXML
    public void handlePlaceBid() {
        try {
            double amount = Double.parseDouble(txtBidAmount.getText().trim());
            // Gọi tầng lõi xử lý tiền tệ và đồng bộ luồng
            auctionCore.processBid(amount);

            // Cập nhật lại giá mới lên màn hình sau khi Bid thành công
            lblCurrentPrice.setText(String.format("%,.0f VNĐ", currentProduct.getCurrentPrice()));
            txtBidAmount.clear();
        } catch (NumberFormatException e) {
            SceneManager.getInstance().showPopup("Lỗi", "Số tiền không hợp lệ!");
        }
    }

    @FXML
    public void handleBackToHome() {
        SceneManager.getInstance().switchScene("home.fxml");
    }
}