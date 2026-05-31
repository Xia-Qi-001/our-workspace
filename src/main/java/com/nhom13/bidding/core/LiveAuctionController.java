package com.nhom13.bidding.core;

import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import javafx.application.Platform;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveAuctionController {
    private Product product;
    private ScheduledExecutorService scheduler;

    public LiveAuctionController(Product product) {
        this.product = product;
    }

    // Kích hoạt luồng đếm ngược chạy ngầm từng giây
    public void startAuctionTimer() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // Kiểm tra mốc thời gian chốt phiên của Huy
            if (LocalDateTime.now().isAfter(product.getEndTime()) || !"Đang đấu giá".equals(product.getStatus())) {
                endAuction();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    // Đồng bộ hóa luồng giao dịch tiền và giá
    // Đồng bộ hóa luồng giao dịch tiền và giá
    public synchronized void processBid(double amount) {
        User currentUser = SessionManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            SceneManager.getInstance().showPopup("Lỗi Xác Thực", "Cậu phải đăng nhập để tham gia!");
            return;
        }

        // Kiểm tra logic chặn chủ phòng tự nâng giá
        if (currentUser.getId() == product.getSellerId()) {
            SceneManager.getInstance().showPopup("Từ Chối Giao Dịch", "Cậu không thể tự đặt giá cho sản phẩm của mình.");
            return;
        }

        // VÁ LỖI LOGIC TRỪ TIỀN (Tính Delta)
        double deltaAmount = amount;

        // Nếu chính user này đang giữ top 1 mà muốn tự nâng giá để đè người khác
        if (product.getCurrentWinnerId() == currentUser.getId()) {
            deltaAmount = amount - product.getCurrentPrice();
        }

        // Kiểm tra ví tiền với số tiền chênh lệch (delta), không phải toàn bộ amount
        if (!currentUser.canAfford(deltaAmount)) {
            SceneManager.getInstance().showPopup("Số Dư Không Đủ", "Ví tài khoản không đủ tiền để thực hiện mức Bid này.");
            return;
        }

        try {
            // Gọi Product thực hiện kiểm tra bước giá (placeBid có thể ném ra Exception)
            product.placeBid(amount, currentUser.getId());

            // Chỉ trừ số tiền chênh lệch
            currentUser.deductMoney(deltaAmount);

            SceneManager.getInstance().showPopup("Đặt Giá Thành Công",
                    "Hệ thống ghi nhận mức giá mới: " + String.format("%,.0f VNĐ", amount));

        } catch (Exception e) {
            // Bắt trọn ngoại lệ lọt ra từ Model của Huy và in lên UI
            SceneManager.getInstance().showPopup("Giao Dịch Thất Bại", e.getMessage());
        }
    }

    public void endAuction() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown(); // Giải phóng Thread ngầm
        }

        product.checkAndSetStatus(); // Chốt trạng thái Đã bán/Hủy bên Model

        // Trả luồng hiển thị thông báo về Main Thread JavaFX để tránh crash ứng dụng
        Platform.runLater(() -> {
            SceneManager.getInstance().showPopup("Thông Báo Phiên Đấu",
                    "Phiên đấu giá vật phẩm [" + product.getName() + "] đã chính thức khép lại!");
        });
    }
}