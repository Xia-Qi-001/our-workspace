package com.nhom13.bidding.core;

import com.nhom13.bidding.dao.ProductDAO;
import com.nhom13.bidding.model.Product;
import java.util.List;

public class ProductManager {
    private static ProductManager instance;

    // Nối với cầu nối DB
    private ProductDAO productDAO = new ProductDAO();

    // Biến lưu vết món đồ đang được click chọn để xem chi tiết
    private int selectedProductId = -1;

    private ProductManager() {}

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    // GỌI THẲNG XUỐNG DB THAY VÌ LẤY TỪ LIST ẢO
    public List<Product> getActiveProducts() {
        return productDAO.getAllActiveProducts();
    }

    // Trạm trung chuyển: Gọi xuống DAO để lấy đồ theo ID
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    public void setSelectedProductId(int id) {
        this.selectedProductId = id;
    }

    public int getSelectedProductId() {
        return this.selectedProductId;
    }
}