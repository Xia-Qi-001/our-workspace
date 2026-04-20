package com.nhom13.bidding;

import java.util.HashMap;

/**
 * Product - Lớp đại diện cho một sản phẩm trong hệ thống đấu giá.
 * Giữ nguyên logic xử lý thời gian, hình ảnh và bảo mật từ lớp Item cũ.
 */
public class Product {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private double startingPrice;
    private double currentPrice;
    private boolean approved = false;
    private double stepPrice;
    private String currentWinnerId;
    private String sellerId;
    private long endTime;

    public Product(String id, String name, String description, double startingPrice, double stepPrice, String sellerId, long durationInSeconds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice; // Giá hiện tại bắt đầu bằng giá khởi điểm

        // Gán các giá trị mới từ tham số truyền vào
        this.stepPrice = stepPrice;
        this.sellerId = sellerId;
        this.currentWinnerId = "None"; // Mặc định chưa có người thắng

        // Tính toán thời điểm kết thúc dựa trên durationInSeconds
        this.endTime = System.currentTimeMillis() + (durationInSeconds * 1000);
    }

    // Hàm kiểm tra thời gian
    public boolean isBiddingActive() {
        return System.currentTimeMillis() < this.endTime && this.approved;
    }

    /**
     * Kiểm tra giá đặt mới có hợp lệ theo bước giá hay không.
     */
    public boolean isValidBid(double newBid) {
        return newBid >= (this.currentPrice + this.stepPrice);
    }

    /**
     * Cập nhật thông tin người thắng và giá hiện tại.
     */
    public void updateBid(double newBid, String bidderId) {
        this.currentPrice = newBid;
        this.currentWinnerId = bidderId;
    }

    // --- Các phương thức Setter có kiểm tra logic ---

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setId(String id) {
        if (id != null && !id.isEmpty()) {
            this.id = id;
        }
    }

    public void setImagePath(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            String lowerPath = imagePath.toLowerCase();
            if (lowerPath.endsWith(".jpg") || lowerPath.endsWith(".png") || lowerPath.endsWith(".jpeg")) {
                this.imagePath = imagePath;
            } else {
                System.out.println("Lỗi: Định dạng ảnh không hỗ trợ (chỉ nhận các file có đuôi .jpg, .png, .jpeg)");
            }
        }
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setStartingPrice(double startingPrice) {
        if (startingPrice < 0) {
            System.out.println("Lỗi: Giá không được âm");
            this.startingPrice = 0;
        } else {
            this.startingPrice = startingPrice;
        }
    }

    // --- Các hàm Getter ---

    public String getName() { return name; }
    public String getId() { return this.id; }
    public String getImagePath() { return this.imagePath; }
    public boolean isApproved() { return approved; }
    public double getCurrentPrice() { return this.currentPrice; }
    public String getDescription() { return description; }
    public double getStartingPrice() { return startingPrice; }
    public double getStepPrice() { return stepPrice; }
    public String getCurrentWinnerId() { return currentWinnerId; }
    public String getSellerId() { return sellerId; }
    public long getEndTime() { return endTime; }

    // --- Các hàm Setter còn lại ---

    public void setDescription(String description) { this.description = description; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public void setStepPrice(double stepPrice) { this.stepPrice = stepPrice; }
    public void setCurrentWinnerId(String currentWinnerId) { this.currentWinnerId = currentWinnerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    /**
     * Đặt giá (bid) cho sản phẩm đấu giá.
     */
    public void placeBid(double newBid, String bidderId){
        updateBid(newBid, bidderId);
        System.out.println("Đặt giá thành công! Sản phẩm: " + this.name
                + " | Giá mới: " + this.currentPrice
                + " | Người đặt: " + this.currentWinnerId);
    }
}

/**
 * ProductManager - Lớp quản lý danh sách các sản phẩm đấu giá.
 */
class ProductManager {
    private HashMap<String, Product> productList = new HashMap<>();

    // Hàm thêm các sản phẩm đấu giá
    public void addProduct(Product product) {
        if (productList.containsKey(product.getId())) {
            System.out.println("Lỗi: Mã sản phẩm đã tồn tại");
        } else {
            productList.put(product.getId(), product);
            System.out.println("Thêm thành công: " + product.getName());
        }
    }

    // Hàm duyệt các sản phẩm để đưa ra đấu giá
    public void approveProduct(String id) {
        Product product = productList.get(id);
        if (product != null) {
            product.setApproved(true);
            System.out.println("Sản phẩm " + product.getName() + " đã được duyệt thành công");
        } else {
            System.out.println("Lỗi: Không tìm thấy mã sản phẩm để duyệt");
        }
    }

    public void showMarketplace() {
        System.out.println("--- DANH SÁCH SẢN PHẨM ĐANG ĐẤU GIÁ ---");
        for (Product product : productList.values()) {
            if (product.isApproved()) {
                System.out.println(product.getId() + " - " + product.getName() + ": " + product.getCurrentPrice());
            }
        }
    }

    // Hàm xóa sản phẩm
    public void deleteProduct(String id) {
        if (productList.containsKey(id)) {
            productList.remove(id);
            System.out.println("Xóa sản phẩm thành công");
        } else {
            System.out.println("Lỗi: Không tìm thấy sản phẩm");
        }
    }

    // Hàm lấy danh sách sản phẩm theo Seller
    public void showMyProducts(String sellerId) {
        System.out.println("Sản phẩm của bạn:");
        for (Product product : productList.values()) {
            if (product.getSellerId().equals(sellerId))
                System.out.println(product.getName());
        }
    }

    public void checkAndCloseExpiredProducts() {
        for (Product product : productList.values()) {
            if (System.currentTimeMillis() > product.getEndTime() && product.isApproved()) {
                System.out.println("Phiên đấu giá sản phẩm " + product.getName() + " đã kết thúc");
            }
        }
    }
}