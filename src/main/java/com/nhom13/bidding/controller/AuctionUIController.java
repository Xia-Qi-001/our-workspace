package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.ProductManager;
import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Platform;

public class AuctionUIController {

    @FXML private Label lblProductName;
    @FXML private Label lblCurrentPrice;
    @FXML private Label lblEndTime;
    @FXML private TextField txtBidAmount;

    private AuctionController logicController;
    private Product currentProduct;

    @FXML
    public void initialize() {
        int selectedId = ProductManager.getInstance().getSelectedProductId();
        currentProduct = ProductManager.getInstance().getProductById(selectedId);
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // [DEBUG] - Bắt lỗi xem ID có bị mất khi nạp từ DB lên không
        System.out.println("ID người đang login: " + currentUser.getId());
        System.out.println("ID chủ sản phẩm (lấy từ Manager): " + currentProduct.getSellerId());

        if (currentProduct != null) {
            logicController = new AuctionController(currentProduct, currentUser);

            lblProductName.setText(currentProduct.getName());
            lblCurrentPrice.setText(String.format("%,.0f VNĐ", currentProduct.getCurrentPrice()));

            // Định dạng thời gian hiển thị ban đầu
            int totalSecs = logicController.getRemainingTime();
            String timeFormatted = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60);
            lblEndTime.setText(timeFormatted);

            startCountdownTimer();
        }
    }

    private void startCountdownTimer() {
        Thread timerThread = new Thread(() -> {
            while (logicController.isTimeValid()) {
                try {
                    Thread.sleep(1000);
                    logicController.tickDown();

                    // Giao diện chỉ cập nhật trong luồng JavaFX
                    Platform.runLater(() -> {
                        int totalSecs = logicController.getRemainingTime();
                        String timeFormatted = String.format("%02d:%02d:%02d", totalSecs / 3600, (totalSecs % 3600) / 60, totalSecs % 60);
                        lblEndTime.setText(timeFormatted);

                        // NẾU HẾT GIỜ -> THỰC HIỆN KHÓA SỔ
                        if (!logicController.isTimeValid()) {
                            lblEndTime.setText("ĐÃ KẾT THÚC");
                            txtBidAmount.setDisable(true);

                            // BƯỚC 1: Ép chọc xuống DB lấy dữ liệu tươi nhất (Để tránh lỗi báo không ai mua)
                            int selectedId = ProductManager.getInstance().getSelectedProductId();
                            Product freshProduct = ProductManager.getInstance().getProductById(selectedId);

                            if (freshProduct != null) {
                                currentProduct.setCurrentPrice(freshProduct.getCurrentPrice());
                                currentProduct.setCurrentWinnerId(freshProduct.getCurrentWinnerId());
                            }

                            // BƯỚC 2: Khóa trạng thái sản phẩm dưới MySQL
                            logicController.endAuction();

                            // BƯỚC 3: Bật Popup thông báo kết quả
                            double finalPrice = currentProduct.getCurrentPrice();
                            int winnerId = currentProduct.getCurrentWinnerId();
                            int currentUserId = SessionManager.getInstance().getCurrentUser().getId();

                            if (winnerId > 0) {
                                if (winnerId == currentUserId) {
                                    SceneManager.getInstance().showPopup("Chúc mừng!", "Bạn đã sở hữu sản phẩm này với giá " + String.format("%,.0f VNĐ", finalPrice));
                                } else {
                                    SceneManager.getInstance().showPopup("Phiên đấu giá kết thúc", "Món đồ này đã được bán thành công cho ID: " + winnerId);
                                }
                            } else {
                                SceneManager.getInstance().showPopup("Phiên đấu giá kết thúc", "Phiên đấu giá đã kết thúc nhưng không có ai tham gia đặt giá.");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        timerThread.setDaemon(true);
        timerThread.start();
    }

    @FXML
    public void handlePlaceBid() {
        try {
            double amount = Double.parseDouble(txtBidAmount.getText().trim());

            // Ép Controller xử lý logic
            String result = logicController.processBid(SessionManager.getInstance().getCurrentUser(), amount);

            // Bắt lỗi mượt mà
            if ("SUCCESS".equals(result)) {
                lblCurrentPrice.setText(String.format("%,.0f VNĐ", currentProduct.getCurrentPrice()));
                txtBidAmount.clear();
                SceneManager.getInstance().showPopup("Thành công", "Đã đặt giá! Bạn đang tạm dẫn đầu.");
            } else {
                // Quăng câu chửi ra màn hình và DỪNG LUỒNG
                SceneManager.getInstance().showPopup("Lỗi hệ thống", result);
            }

        } catch (NumberFormatException e) {
            SceneManager.getInstance().showPopup("Lỗi", "Vui lòng nhập số tiền hợp lệ!");
        }
    }

    @FXML
    public void handleBackToHome() {
        SceneManager.getInstance().switchScene("home.fxml");
    }
}