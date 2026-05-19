package com.nhom13.bidding.core; // Đã sửa đúng "hộ khẩu"

import com.nhom13.bidding.model.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductManager {

    private static ProductManager instance;
    private HashMap<Integer, Product> productList = new HashMap<>();
    private int selectedProductId = -1;
    private int nextId = 3; // Vì đã mồi sẵn 2 món đồ nên ID tiếp theo là 3

    private ProductManager() {
        // BƠM DỮ LIỆU MỒI VÀO ĐÂY ĐỂ TEST UI
        Product p1 = new Product(1, "Laptop ThinkPad T14", 15000000, 500000, 2, LocalDateTime.now().plusHours(24));
        p1.setStatus("Đang đấu giá"); // Ép trạng thái chuẩn để HomeUI lọc được

        Product p2 = new Product(2, "Mô hình Gundam", 2000000, 100000, 2, LocalDateTime.now().plusHours(12));
        p2.setStatus("Đang đấu giá");

        productList.put(1, p1);
        productList.put(2, p2);
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        // Ép tự động duyệt để test cho lẹ (nếu cậu muốn test thực tế luôn)
        product.setStatus("Đang đấu giá");
        productList.put(product.getId(), product);
        System.out.println("Thêm sản phẩm thành công: " + product.getName());
    }

    public Product getProductById(int id) {
        return productList.get(id);
    }

    public List<Product> getActiveProducts() {
        List<Product> activeProducts = new ArrayList<>();
        for (Product product : productList.values()) {
            if ("Đang đấu giá".equals(product.getStatus())) {
                activeProducts.add(product);
            }
        }
        return activeProducts;
    }

    public int generateNextId() {
        return nextId++;
    }

    public void setSelectedProductId(int productId) {
        this.selectedProductId = productId;
    }

    public int getSelectedProductId() {
        return selectedProductId;
    }
}