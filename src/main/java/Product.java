import java.util.HashMap;

public class Product {
    private String id;
    private String name;
    private String imagePath;
    private double startingPrice;
    private double currentPrice;
    public Product(String id, String name, double startingPrice, double currentPrice) {
        this.id = id;
        this.name = name;
        this.startingPrice = startingPrice;
        this.currentPrice=startingPrice;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return this.id;
    }
}
class ProductManager {
    private HashMap<String, Product> productList = new HashMap<>();

    public void addProduct(Product product) {
        if (productList.containsKey(product.getId())) {
            System.out.println("Lỗi: Mã sản phẩm đã tồn tại!");
        }
        else {
            productList.put(product.getId(), product);
            System.out.println("Thêm thành công: " + product.getName());
        }
    }
}
