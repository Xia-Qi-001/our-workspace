package com.nhom13.bidding;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

/**
 * SellerUIController - Controller cho Màn hình Đăng bán sản phẩm.
 *
 * Cho phép người dùng (Seller) tạo sản phẩm đấu giá mới.
 * Sau khi đăng bán thành công, chuyển về trang Home.
 *
 * Nguyên tắc:
 *   - UI chỉ validate cơ bản và gọi Manager
 *   - Mọi chuyển cảnh qua SceneManager.getInstance().switchScene()
 *   - Không dùng new Stage().show()
 */
public class SellerUIController {

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtStartPrice;

    // --- Logic Nghiệp vụ ---

    /**
     * Xử lý sự kiện khi người dùng nhấn nút "Đăng bán".
     *
     * Luồng xử lý:
     *   1. Validate: Tên không để trống, giá khởi điểm phải là số dương.
     *   2. Tạo đối tượng Product mới với sellerId từ SessionManager.
     *   3. Thêm sản phẩm vào ProductManager.
     *   4. Chuyển về trang Home.
     */
    @FXML
    private void handleCreateAuction() {
        // --- Bước 1: Lấy dữ liệu từ UI ---
        String productName = txtProductName.getText().trim();
        String startPriceText = txtStartPrice.getText().trim();

        // --- Bước 2: Validate dữ liệu ---
        if (productName.isEmpty()) {
            showAlert(AlertType.WARNING,
                    "Thiếu thông tin",
                    "Tên sản phẩm không được để trống!",
                    "Vui lòng nhập tên sản phẩm.");
            return;
        }

        double startPrice;
        try {
            startPrice = Double.parseDouble(startPriceText);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING,
                    "Dữ liệu không hợp lệ",
                    "Giá khởi điểm phải là một số!",
                    "Vui lòng nhập giá hợp lệ (ví dụ: 100000).");
            return;
        }

        if (startPrice <= 0) {
            showAlert(AlertType.WARNING,
                    "Dữ liệu không hợp lệ",
                    "Giá khởi điểm phải là số dương!",
                    "Vui lòng nhập giá lớn hơn 0.");
            return;
        }

        // --- Bước 3: Tạo đối tượng Product ---
        int sellerId = SessionManager.getInstance().getCurrentUser().getId();
        int newId = ProductManager.getInstance().generateNextId();

        // Giá trị mặc định: stepPrice = 10% giá khởi điểm, thời gian = 24 giờ
        double stepPrice = startPrice * 0.1;
        LocalDateTime endTime = LocalDateTime.now().plusHours(24);

        Product newProduct = new Product(newId, productName, startPrice, stepPrice, sellerId, endTime);
        // Sản phẩm mới có status mặc định "Chờ duyệt" (được set trong constructor Product)

        // --- Bước 4: Thêm sản phẩm vào hệ thống ---
        ProductManager.getInstance().addProduct(newProduct);

        // --- Bước 5: Thông báo thành công và chuyển về Home ---
        showAlert(AlertType.INFORMATION,
                "Thành công",
                "Đăng bán sản phẩm thành công!",
                "Sản phẩm '" + productName + "' đã được tạo với giá khởi điểm " + startPrice + " VNĐ.\n"
                + "Sản phẩm đang ở trạng thái \"Chờ duyệt\".");

        SceneManager.getInstance().switchScene("HomeView.fxml");
    }

    // --- Điều hướng ---

    /**
     * Xử lý khi người dùng nhấn nút "Quay lại" để về trang Home.
     */
    @FXML
    private void handleBackToHome() {
        SceneManager.getInstance().switchScene("HomeView.fxml");
    }

    // --- Tiện ích ---

    /**
     * Hiển thị Alert trên giao diện JavaFX.
     *
     * @param alertType  Loại Alert (INFORMATION, WARNING, ERROR)
     * @param title      Tiêu đề cửa sổ Alert
     * @param header     Dòng tiêu đề chính
     * @param content    Nội dung chi tiết
     */
    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
