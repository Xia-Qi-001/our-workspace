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

    public void startAuctionTimer() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (LocalDateTime.now().isAfter(product.getEndTime()) || !"Đang đấu giá".equals(product.getStatus())) {
                endAuction();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public synchronized void processBid(double amount) {
        User currentUser = SessionManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            SceneManager.getInstance().showPopup("Lỗi Xác Thực", "Cậu phải đăng nhập để tham gia!");
            return;
        }

        if (currentUser.getId() == product.getSellerId()) {
            SceneManager.getInstance().showPopup("Từ Chối Giao Dịch", "Cậu không thể tự đặt giá cho sản phẩm của mình.");
            return;
        }

        double deltaAmount = amount;

        if (product.getCurrentWinnerId() == currentUser.getId()) {
            deltaAmount = amount - product.getCurrentPrice();
        }

        if (!currentUser.canAfford(deltaAmount)) {
            SceneManager.getInstance().showPopup("Số Dư Không Đủ", "Ví tài khoản không đủ tiền để thực hiện mức Bid này.");
            return;
        }

        try {
            product.placeBid(amount, currentUser.getId());
            currentUser.deductMoney(deltaAmount);
            SceneManager.getInstance().showPopup("Đặt Giá Thành Công",
                    "Hệ thống ghi nhận mức giá mới: " + String.format("%,.0f VNĐ", amount));

        } catch (Exception e) {
            SceneManager.getInstance().showPopup("Giao Dịch Thất Bại", e.getMessage());
        }
    }

    public void endAuction() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }

        product.checkAndSetStatus();

        Platform.runLater(() -> {
            SceneManager.getInstance().showPopup("Thông Báo Phiên Đấu",
                    "Phiên đấu giá vật phẩm [" + product.getName() + "] đã chính thức khép lại!");
        });
    }
}