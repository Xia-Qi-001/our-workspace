package com.nhom13.bidding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ProductManager - Singleton quản lý toàn bộ danh sách sản phẩm đấu giá (Product).
 *
 * Chức năng chính:
 *   - Thêm/lấy sản phẩm
 *   - Lọc sản phẩm đang đấu giá (cho HomeUI)
 *   - Truyền ID sản phẩm đã chọn giữa các màn hình (selectedProductId)
 *
 * Cách sử dụng:
 *   ProductManager.getInstance().addProduct(product);
 *   List<Product> active = ProductManager.getInstance().getActiveProducts();
 */
public class ProductManager {

    // --- Singleton Pattern ---
    private static ProductManager instance;

    private HashMap<Integer, Product> productList = new HashMap<>();
    private int selectedProductId = -1; // ID sản phẩm được chọn (dùng để truyền giữa các màn hình)
    private int nextId = 1; // Auto-increment ID

    private ProductManager() {
        // Private constructor ngăn khởi tạo từ bên ngoài
    }

    /**
     * Lấy instance duy nhất của ProductManager.
     */
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    // --- Quản lý sản phẩm ---

    /**
     * Thêm sản phẩm mới vào hệ thống.
     *
     * @param product Sản phẩm cần thêm
     */
    public void addProduct(Product product) {
        productList.put(product.getId(), product);
        System.out.println("Thêm sản phẩm thành công: " + product.getName());
    }

    /**
     * Lấy sản phẩm theo ID.
     *
     * @param id ID sản phẩm
     * @return Product tìm được, hoặc null nếu không tồn tại
     */
    public Product getProductById(int id) {
        return productList.get(id);
    }

    /**
     * Lọc và trả về danh sách sản phẩm có trạng thái "Đang đấu giá".
     * Được sử dụng bởi HomeUIController để hiển thị sản phẩm trên Chợ.
     *
     * @return Danh sách sản phẩm đang hoạt động
     */
    public List<Product> getActiveProducts() {
        List<Product> activeProducts = new ArrayList<>();
        for (Product product : productList.values()) {
            if ("Đang đấu giá".equals(product.getStatus())) {
                activeProducts.add(product);
            }
        }
        return activeProducts;
    }

    /**
     * Tạo ID tự động tăng cho sản phẩm mới.
     *
     * @return ID mới chưa được sử dụng
     */
    public int generateNextId() {
        return nextId++;
    }

    // --- Cơ chế truyền ID sản phẩm giữa các màn hình ---

    /**
     * Lưu ID sản phẩm đã chọn (khi click vào sản phẩm trên trang Home).
     * Màn hình AuctionView sẽ đọc giá trị này để biết đang đấu giá sản phẩm nào.
     *
     * @param productId ID sản phẩm đã chọn
     */
    public void setSelectedProductId(int productId) {
        this.selectedProductId = productId;
    }

    /**
     * Lấy ID sản phẩm đã chọn.
     *
     * @return ID sản phẩm, hoặc -1 nếu chưa chọn
     */
    public int getSelectedProductId() {
        return selectedProductId;
    }
}
