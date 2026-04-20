package com.nhom13.bidding.controller;

import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;

public class AuctionController {
    private int remainingTime;
    private Product product;

    public AuctionController(Product product, int remainingTime) {
        this.product = product;
        this.remainingTime = remainingTime;
    }

    public boolean isTimeValid() {
        return this.remainingTime > 0;
    }

    public void processBid(User user, double amount) {
        System.out.println("\n[Lệnh Bid] " + user.getUsername() + " trả giá: " + amount);

        // Lưới lọc 1: Thời gian
        if (!isTimeValid()) {
            System.out.println("-> Thất bại: Đã hết thời gian đấu giá!");
            return;
        }

        // Lưới lọc 2: Luật bước giá
        if (!product.isValidBid(amount)) {
            System.out.println("-> Thất bại: Mức giá chưa hợp lệ (Phải >= " + (product.getCurrentPrice() + product.getStepPrice()) + ")");
            return;
        }

        // Lưới lọc 3: Tiền trong ví
        if (!user.canAfford(amount)) {
            System.out.println("-> Thất bại: Số dư trong ví không đủ!");
            return;
        }

        // Vượt qua 3 lưới lọc -> Trừ tiền và Cập nhật Top 1
        user.deductMoney(amount);
        product.updateBid(amount, user.getId());

        System.out.println("-> Thành công: " + user.getUsername() + " tạm dẫn đầu!");
    }

    public void endAuction() {
        System.out.println("\n=== PHIÊN ĐẤU GIÁ KẾT THÚC ===");
        if (product.getCurrentWinnerId() != -1) {
            System.out.println("Sản phẩm: " + product.getName() + " đã được bán với giá " + product.getCurrentPrice());
        } else {
            System.out.println("Sản phẩm không có ai mua.");
        }
    }
    // Thêm hàm này để mỗi giây trôi qua thì trừ thời gian đi 1
    public void tickTime() {
        if (this.remainingTime > 0) {
            this.remainingTime--;
        }
    }

    // Thêm hàm này để giao diện lấy số giây hiển thị lên màn hình
    public int getRemainingTime() {
        return this.remainingTime;
    }
}