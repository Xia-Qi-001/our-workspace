package com.nhom13.bidding.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultUIController {

    @FXML private Label productLabel;
    @FXML private Label winnerLabel;
    @FXML private Label priceLabel;

    // Hàm này như một cái "phễu" để màn hình cũ đổ dữ liệu sang màn hình này
    public void setAuctionResult(String productName, String winnerName, double finalPrice) {
        productLabel.setText("Sản phẩm: " + productName);

        if (winnerName.equals("Chưa có ai")) {
            winnerLabel.setText("Kết quả: Không có ai mua!");
            winnerLabel.setStyle("-fx-text-fill: #7f8c8d;"); // Đổi màu xám
        } else {
            winnerLabel.setText("Người thắng: " + winnerName + " 🎉");
        }

        priceLabel.setText("Mức giá chốt: " + finalPrice + " $");
    }
}