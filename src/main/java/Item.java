import java.util.HashMap;

public class Item {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private double startingPrice;
    private double currentPrice;
    private boolean approved = false;
    public Item(String id, String name,String description, double startingPrice, double currentPrice) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.startingPrice = startingPrice;
        this.currentPrice=startingPrice;
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
    public boolean isApproved() {
        return approved;
    }
    public double getCurrentPrice(){
        return this.currentPrice;
    }
}
class ItemManager {
    private HashMap<String, Item> itemList = new HashMap<>();

    public void addItem(Item item) {
        if (itemList.containsKey(item.getId())) {
            System.out.println("Lỗi: Mã sản phẩm đã tồn tại");
        }
        else {
            itemList.put(item.getId(), item);
            System.out.println("Thêm thành công: " + item.getName());
        }
    }
    public void approveItem(String id) {
        Item item = itemList.get(id);
        if (item != null) {
            item.setApproved(true);
            System.out.println("Sản phẩm " + item.getName() + " đã được duyệt thành công");
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
}
