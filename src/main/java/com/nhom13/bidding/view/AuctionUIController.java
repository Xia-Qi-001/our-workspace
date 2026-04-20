package com.nhom13.bidding.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.nhom13.bidding.controller.AuctionController;
import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AuctionUIController {

    @FXML private Label productNameLabel;
    @FXML private Label productDescLabel;
    @FXML private Label currentPriceLabel;
    @FXML private Label winnerLabel;
    @FXML private Label timeLabel;
    @FXML private Label user1BalanceLabel;
    @FXML private Label user2BalanceLabel;
    @FXML private TextField bidInput;
    @FXML private Label messageLabel;

    private AuctionController logicController;
    private Product product;
    private User user1;
    private User user2;
    private Timeline timeline;

    @FXML
    public void initialize() {
        product = new Product(1, "Laptop ASUS ROG", 1000.0, 100.0);
        product.setDescription("Bản i7-13700H, RTX 4060, RAM 16GB");

        user1 = new User(101, "HuuBang", 5000.0);
        user2 = new User(102, "Hoang", 3000.0);

        // Thiết lập thời gian đấu giá là 60 giây cho nhanh thấy kết quả nhé
        logicController = new AuctionController(product, 60);

        refreshUI();
        startTimer(); // Khởi động đếm ngược
    }

    // --- LOGIC ĐẾM NGƯỢC ---
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            logicController.tickTime();
            int timeLeft = logicController.getRemainingTime();
            timeLabel.setText("⏳ Còn lại: " + timeLeft + " giây");

            // KHI HẾT GIỜ -> CHUYỂN CẢNH
            if (timeLeft <= 0) {
                timeline.stop();

                try {
                    // 1. Tải giao diện Tổng kết (ResultView.fxml)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResultView.fxml"));
                    Parent root = loader.load();

                    // 2. Lấy tên người thắng hiện tại
                    String finalWinner = "Chưa có ai";
                    if (product.getCurrentWinnerId() == user1.getId()) finalWinner = user1.getUsername();
                    if (product.getCurrentWinnerId() == user2.getId()) finalWinner = user2.getUsername();

                    // 3. Ném dữ liệu sang màn hình mới
                    ResultUIController resultController = loader.getController();
                    resultController.setAuctionResult(product.getName(), finalWinner, product.getCurrentPrice());

                    // 4. Lấy cái Khung cửa sổ (Stage) hiện tại và Đổi màn hình (Scene)
                    Stage stage = (Stage) timeLabel.getScene().getWindow();
                    stage.setScene(new Scene(root, 600, 450));

                } catch (Exception e) {
                    System.err.println("Lỗi không mở được màn hình kết quả!");
                    e.printStackTrace();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handleBidUser1() { executeBid(user1); }

    @FXML
    public void handleBidUser2() { executeBid(user2); }

    private void executeBid(User bidder) {
        // Cú chặn đầu tiên: Nếu hết giờ thì đuổi thẳng cổ, không tính toán gì nữa
        if (!logicController.isTimeValid()) {
            showMessage("❌ Đã hết giờ! Không thể đặt giá.", "#e74c3c");
            return;
        }

        try {
            double amount = Double.parseDouble(bidInput.getText());

            if (!product.isValidBid(amount)) {
                showMessage("❌ Lỗi: Mức giá phải >= " + (product.getCurrentPrice() + product.getStepPrice()) + " $", "#e74c3c");
                return;
            }

            if (!bidder.canAfford(amount)) {
                showMessage("❌ Lỗi: Ví của " + bidder.getUsername() + " không đủ tiền!", "#e74c3c");
                return;
            }

            int oldWinnerId = product.getCurrentWinnerId();
            if (oldWinnerId == user1.getId()) user1.refundMoney(product.getCurrentPrice());
            else if (oldWinnerId == user2.getId()) user2.refundMoney(product.getCurrentPrice());

            logicController.processBid(bidder, amount);

            showMessage("✅ Thành công: " + bidder.getUsername() + " đang giữ Top 1!", "#27ae60");
            bidInput.clear();
            refreshUI();

        } catch (NumberFormatException e) {
            showMessage("⚠️ Lỗi: Vui lòng nhập số hợp lệ!", "#f39c12");
        }
    }

    private void refreshUI() {
        productNameLabel.setText("Sản phẩm: " + product.getName());
        productDescLabel.setText(product.getDescription());
        currentPriceLabel.setText("Giá hiện tại: " + product.getCurrentPrice() + " $");

        String top1Name = "Chưa có ai";
        if (product.getCurrentWinnerId() == user1.getId()) top1Name = user1.getUsername();
        if (product.getCurrentWinnerId() == user2.getId()) top1Name = user2.getUsername();
        winnerLabel.setText("Đang dẫn đầu: " + top1Name);

        user1BalanceLabel.setText("Ví: " + user1.getBalance() + " $");
        user2BalanceLabel.setText("Ví: " + user2.getBalance() + " $");
    }

    private void showMessage(String msg, String hexColor) {
        messageLabel.setText(msg);
        messageLabel.setStyle("-fx-text-fill: " + hexColor + ";");
    }
}