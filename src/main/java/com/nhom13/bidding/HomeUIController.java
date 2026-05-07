package com.nhom13.bidding;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * HomeUIController - Controller cho Màn hình Chợ (Marketplace).
 *
 * Hiển thị danh sách sản phẩm đang đấu giá dưới dạng lưới Card.
 * Khi click vào sản phẩm, chuyển sang màn hình AuctionView.
 *
 * Nguyên tắc:
 *   - UI không tự xử lý logic: chỉ gọi ProductManager để lấy dữ liệu
 *   - Mọi chuyển cảnh qua SceneManager.getInstance().switchScene()
 *   - Không dùng new Stage().show()
 */
public class HomeUIController {

    @FXML
    private GridPane productGrid;

    private static final int COLUMNS = 3; // Số cột trong lưới sản phẩm

    /**
     * Được JavaFX tự động gọi sau khi FXML được load.
     * Khởi tạo giao diện bằng cách load danh sách sản phẩm đang đấu giá.
     */
    @FXML
    public void initialize() {
        loadActiveProducts();
    }

    // --- Logic Load dữ liệu ---

    /**
     * Lấy danh sách sản phẩm từ ProductManager, lọc những sản phẩm có
     * status "Đang đấu giá", tạo Card UI cho mỗi sản phẩm và add vào productGrid.
     */
    private void loadActiveProducts() {
        // Xóa nội dung cũ trước khi load lại
        productGrid.getChildren().clear();

        // Lấy danh sách sản phẩm đang đấu giá từ Manager
        List<Product> activeProducts = ProductManager.getInstance().getActiveProducts();

        if (activeProducts.isEmpty()) {
            // Hiển thị thông báo khi không có sản phẩm
            Label emptyLabel = new Label("Chưa có sản phẩm nào đang đấu giá.");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888888;");
            productGrid.add(emptyLabel, 0, 0, COLUMNS, 1);
            return;
        }

        // Tạo Card UI cho mỗi sản phẩm và add vào grid
        int col = 0;
        int row = 0;
        for (Product product : activeProducts) {
            VBox card = createProductCard(product);
            productGrid.add(card, col, row);

            col++;
            if (col >= COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Tạo một Card UI (VBox) hiển thị thông tin sản phẩm.
     * Card gồm: tên sản phẩm và giá hiện tại, có hiệu ứng hover.
     *
     * @param product Sản phẩm cần hiển thị
     * @return VBox card đại diện cho sản phẩm
     */
    private VBox createProductCard(Product product) {
        // --- Tên sản phẩm ---
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setStyle("-fx-text-fill: #2c3e50;");
        nameLabel.setWrapText(true);

        // --- Giá hiện tại (format số tiền) ---
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        Label priceLabel = new Label("Giá: " + currencyFormat.format(product.getCurrentPrice()) + " VNĐ");
        priceLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        priceLabel.setStyle("-fx-text-fill: #e74c3c;");

        // --- Card container ---
        VBox card = new VBox(8);
        card.getChildren().addAll(nameLabel, priceLabel);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(220);
        card.setPrefHeight(120);
        card.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-border-color: #dcdcdc;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 4, 0, 0, 2);"
        );
        card.setCursor(Cursor.HAND);

        // --- Hiệu ứng hover ---
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #f0f7ff;" +
                "-fx-border-color: #3498db;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.3), 8, 0, 0, 3);"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-border-color: #dcdcdc;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 4, 0, 0, 2);"
        ));

        // --- Click handler: điều hướng sang AuctionView ---
        card.setOnMouseClicked(e -> handleItemClick(product.getId()));

        return card;
    }

    // --- Logic Điều hướng ---

    /**
     * Xử lý khi người dùng click vào một sản phẩm.
     * Lưu ID sản phẩm đã chọn vào ProductManager, sau đó chuyển sang AuctionView.
     *
     * @param productId ID của sản phẩm được click
     */
    private void handleItemClick(int productId) {
        // Lưu ID sản phẩm đã chọn để AuctionView có thể đọc
        ProductManager.getInstance().setSelectedProductId(productId);

        // Chuyển sang màn hình Đấu giá
        SceneManager.getInstance().switchScene("AuctionView.fxml");
    }

    // --- Điều hướng sang trang Đăng bán ---

    /**
     * Xử lý khi người dùng nhấn nút "Đăng bán" trên trang Home.
     * Chuyển sang màn hình SellerView.
     */
    @FXML
    private void handleGoToSeller() {
        SceneManager.getInstance().switchScene("SellerView.fxml");
    }
}
