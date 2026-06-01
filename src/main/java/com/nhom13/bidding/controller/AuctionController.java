package com.nhom13.bidding.controller;

import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import com.nhom13.bidding.dao.ProductDAO;
import com.nhom13.bidding.dao.UserDAO;
import com.nhom13.bidding.core.SessionManager;
import java.time.LocalDateTime;
import java.time.Duration;

public class AuctionController {
    private int remainingTime;
    private Product product;
    private User currentUser;

    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();

    public AuctionController(Product product, User currentUser) {
        this.product = product;
        this.currentUser = currentUser;

        if (product.getEndTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, product.getEndTime());

            this.remainingTime = (int) duration.getSeconds();

            if (this.remainingTime < 0) {
                this.remainingTime = 0;
            }
        } else {
            this.remainingTime = 0;
        }
    }

    public boolean isTimeValid() {
        return remainingTime > 0;
    }

    public String processBid(User user, double amount) {
        if (user == null) return "Vui lòng đăng nhập!";

        // // ==========================================================
        // // 🚨 HỆ THỐNG IN LOG BẮT HUNG THỦ (XEM DƯỚI CONSOLE INTELLIJ)
        // // ==========================================================
        // System.out.println("\n========== [DEBUG ĐẤU GIÁ ĐẬP CHẾT BUG] ==========");
        // System.out.println("-> ID Người đang bấm nút đặt giá (RAM): " + user.getId());
        // System.out.println("-> ID Chủ hàng thực sự của món đồ (RAM): " + product.getSellerId());
        // System.out.println("-> Trạng thái hiện tại của sản phẩm: " + product.getStatus());
        // System.out.println("===================================================\n");

        // // CHỐT CHẶN 1: Khóa primitive cứng bằng cơm ngay tại cửa Controller
        if (user.getId() == product.getSellerId()) {
            return "Bạn không thể tự đặt giá cho sản phẩm của chính mình!";
        }

        if (!isTimeValid()) return "Phiên đấu giá đã kết thúc!";
        if (user.getBalance() < amount) return "Số dư không đủ!";

        // Cập nhật giá mới nhất từ DB để chống đặt đè giá cũ
        double latestPrice = productDAO.getLatestPrice(product.getId());
        if (latestPrice != -1) {
            product.setCurrentPrice(latestPrice);
        }

        if (amount < product.getCurrentPrice() + product.getStepPrice()) {
            return "Giá đặt chưa đủ bước giá tối thiểu!";
        }

        // CHỐT CHẶN 2: Ép chạy qua bộ logic ném Exception của Model Phase 2 cho chắc cú
        try {
            product.placeBid(amount, user.getId());
        } catch (Exception e) {
            return e.getMessage();
        }

        // VƯỢT QUA ĐƯỢC 2 LỚP KHIÊN TRÊN THÌ MỚI XUỐNG DB
        boolean isSuccess = productDAO.updateBidPrice(product.getId(), amount, user.getId());

        if (isSuccess) {
            int oldWinnerId = product.getCurrentWinnerId();
            if (oldWinnerId > 0 && oldWinnerId != user.getId()) {
                System.out.println("Đang hoàn tiền cho user: " + oldWinnerId);
            }

            product.setCurrentPrice(amount);
            product.setCurrentWinnerId(user.getId());
            return "SUCCESS";
        } else {
            return "Lỗi đường truyền, không thể đặt giá lúc này!";
        }
    }

    public void endAuction() {
        this.remainingTime = 0;

        int winnerId = product.getCurrentWinnerId();
        double finalPrice = product.getCurrentPrice();

        if (winnerId > 0) {
            productDAO.updateProductStatus(product.getId(), "Chờ giao hàng");

            boolean isDeducted = userDAO.updateBalance(winnerId, finalPrice);
            if (isDeducted) {
                System.out.println("Đã trừ tiền dưới DB cho User ID: " + winnerId);

                User loginUser = SessionManager.getInstance().getCurrentUser();
                if (loginUser != null && loginUser.getId() == winnerId) {
                    loginUser.setBalance(loginUser.getBalance() - finalPrice);
                    System.out.println("Đã đồng bộ trừ tiền trên RAM cho Session!");
                }
            }
        } else {
            productDAO.updateProductStatus(product.getId(), "Đã kết thúc");
        }
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    public void tickDown() {
        if (remainingTime > 0) {
            remainingTime--;
        }
    }
}
