package com.nhom13.bidding.model;

import java.time.LocalDateTime;

/**
 * Product - Giai đoạn 2: Tích hợp UI và Realtime.
 * Được nâng cấp từ Stage 1 để hỗ trợ đa luồng, quản lý thời gian thực và bảo mật.
 */
public class Product {
    // --- 1. Thuộc tính (Attributes Nâng cấp) ---
    private int id;
    private String name;
    private double startPrice;          // BỔ SUNG: Để khớp với cột start_price trong MySQL
    private double currentPrice;
    private double stepPrice;
    private int currentWinnerId;

    private int sellerId;               // ID người bán (để chặn tự đặt giá)
    private LocalDateTime endTime;      // Thời gian kết thúc (hỗ trợ Countdown)
    private String status;              // Trạng thái: "Chờ duyệt", "Đang đấu giá", "Đã bán", "Hủy"

    private String description;
    private String imagePath;

    // BỔ SUNG: Constructor rỗng bắt buộc phải có để tầng DAO hoạt động
    public Product() {
    }

    public Product(int id, String name, double startingPrice, double stepPrice, int sellerId, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.startPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.stepPrice = stepPrice;
        this.currentWinnerId = -1;

        // Giai đoạn 2 bổ sung
        this.sellerId = sellerId;
        this.endTime = endTime;
        this.status = "Chờ duyệt"; // Trạng thái mặc định ban đầu
    }

    // --- 2. Logic nghiệp vụ (Business Logic) ---

    /**
     * Kiểm tra phiên đấu giá có đang hoạt động hay không.
     * Điều kiện: Trạng thái là "Đang đấu giá" và thời gian hiện tại chưa quá giờ kết thúc.
     */
    public boolean isBiddingActive() {
        return "Đang đấu giá".equals(this.status) && LocalDateTime.now().isBefore(this.endTime);
    }

    /**
     * Chốt chặn bảo mật: Kiểm tra xem người đặt giá có phải chủ đồ (Seller) không.
     */
    public boolean isNotSeller(int bidderId) {
        return bidderId != this.sellerId;
    }

    public boolean isValidBid(double newBid) {
        return newBid >= (this.currentPrice + this.stepPrice);
    }

    // --- 3. Tương tác đa luồng và Realtime (Multi-thread) ---

    /**
     * Cập nhật Thread-safe: Sử dụng synchronized để đảm bảo khi nhiều người cùng Bid một lúc,
     * dữ liệu giá và người thắng không bị sai lệch (Race condition).
     */
    public synchronized void updateBid(double newBid, int userId) {
        this.currentPrice = newBid;
        this.currentWinnerId = userId;
    }

    // --- 4. Kết nối hệ thống (System Integration Hooks) ---

    /**
     * Phương thức đặt giá tổng hợp: Sử dụng bởi Controller sau khi lấy UserID từ SessionManager.
     */
    public void placeBid(double newBid, int bidderId) throws Exception {
        if (!isNotSeller(bidderId)) {
            throw new Exception("Bạn không thể đặt giá cho sản phẩm của chính mình.");
        }
        if (!isBiddingActive()) {
            throw new Exception("Phiên đấu giá hiện không khả dụng hoặc đã kết thúc.");
        }
        if (!isValidBid(newBid)) {
            throw new Exception("Giá đặt phải lớn hơn giá hiện tại ít nhất " + stepPrice);
        }

        // Cập nhật an toàn
        updateBid(newBid, bidderId);
    }

    /**
     * Tự động kiểm tra và cập nhật trạng thái khi hết thời gian.
     * LiveAuctionController sẽ gọi hàm này trong TimerThread.
     */
    public void checkAndSetStatus() {
        if (LocalDateTime.now().isAfter(this.endTime) && "Đang đấu giá".equals(this.status)) {
            if (this.currentWinnerId != -1) {
                this.status = "Đã bán";
            } else {
                this.status = "Hủy";
            }
        }
    }

    // --- Getters & Setters (Đã bổ sung đầy đủ các Setter thiếu) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getStartPrice() { return startPrice; }
    public void setStartPrice(double startPrice) { this.startPrice = startPrice; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double getStepPrice() { return stepPrice; }
    public void setStepPrice(double stepPrice) { this.stepPrice = stepPrice; }

    public int getCurrentWinnerId() { return currentWinnerId; }
    public void setCurrentWinnerId(int currentWinnerId) { this.currentWinnerId = currentWinnerId; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}