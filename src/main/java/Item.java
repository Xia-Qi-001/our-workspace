import java.util.HashMap;

public class Item {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private double startingPrice;
    private double currentPrice;
    private boolean approved = false;
    private double bidIncrement;
    private String currentWinnerId;
    private String sellerId;
    private long endTime;
    public Item(String id, String name, String description, double startingPrice, double bidIncrement, String sellerId, long durationInSeconds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice; // Giá hiện tại bắt đầu bằng giá khởi điểm

        // Gán các giá trị mới từ tham số truyền vào
        this.bidIncrement = bidIncrement;
        this.sellerId = sellerId;
        this.currentWinnerId = "None"; // Mặc định chưa có người thắng

        // Tính toán thời điểm kết thúc dựa trên durationInSeconds
        this.endTime = System.currentTimeMillis() + (durationInSeconds * 1000);
    }
    //Hàm kiểm tra thời gian
    public boolean isBiddingActive() {
        return System.currentTimeMillis() < this.endTime && this.approved;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }
    public void setId(String id){
        if (id != null && !id.isEmpty()) {
            this.id=id;
        }
    }
    public void setImagePath(String imagePath){
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

    public String getName() {
        return name;
    }
    public String getId() {
        return this.id;
    }
    public String getImagePath(){
        return this.imagePath;
    }
    public boolean isApproved() {
        return approved;
    }
    public double getCurrentPrice(){
        return this.currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(double bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public String getCurrentWinnerId() {
        return currentWinnerId;
    }

    public void setCurrentWinnerId(String currentWinnerId) {
        this.currentWinnerId = currentWinnerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
class ItemManager {
    private HashMap<String, Item> itemList = new HashMap<>();
    //Hàm thêm các sản phẩm đấu giá
    public void addItem(Item item) {
        if (itemList.containsKey(item.getId())) {
            System.out.println("Lỗi: Mã sản phẩm đã tồn tại");
        } else {
            itemList.put(item.getId(), item);
            System.out.println("Thêm thành công: " + item.getName());
        }
    }
    //Hàm duyệt các sản phẩm để đưa ra đấu giá
    public void approveItem(String id) {
        Item item = itemList.get(id);
        if (item != null) {
            item.setApproved(true);
            System.out.println("Sản phẩm " + item.getName() + " đã được duyệt thành công");
        } else if (item.getImagePath() == null) {
            System.out.println("Cảnh báo: Sản phẩm chưa có ảnh hợp lệ nhưng vẫn được thêm.");
            itemList.put(item.getId(), item);
        } else {
            System.out.println("Lỗi: Không tìm thấy mã sản phẩm để duyệt");
        }
    }

    public void showMarketplace() {
        System.out.println("--- DANH SÁCH SẢN PHẨM ĐANG ĐẤU GIÁ ---");
        for (Item item : itemList.values()) {
            if (item.isApproved()) {
                System.out.println(item.getId() + " - " + item.getName() + ": " + item.getCurrentPrice());
            }
        }
    }
    // Hàm xóa sản phẩm
    public void deleteItem(String id) {
        if (itemList.containsKey(id)) {
            itemList.remove(id);
            System.out.println("Xóa sản phẩm thành công");
        } else {
            System.out.println("Lỗi: Không tìm thấy sản phẩm");
        }
    }
    // Hàm lấy danh sách sản phẩm theo Seller (để Seller tự quản lý đồ của mình)
    public void showMyItems(String sellerId) {
        System.out.println("Sản phẩm của bạn:");
        for (Item item : itemList.values()) {
            if(item.getSellerId().equals(sellerId))
            System.out.println(item.getName());
        }
    }

    public void checkAndCloseExpiredItems() {
        for (Item item : itemList.values()) {
            if (System.currentTimeMillis() > item.getEndTime() && item.isApproved()) {
                // Logic: Chốt người thắng cuộc, chuyển trạng thái thành SOLD
                System.out.println("Phiên đấu giá " + item.getName() + " đã kết thúc");
            }
        }
    }
}
