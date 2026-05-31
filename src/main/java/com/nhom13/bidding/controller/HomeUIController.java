package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.ProductManager;
import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import com.nhom13.bidding.dao.ProductDAO;
import com.nhom13.bidding.dao.UserDAO;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class HomeUIController {

    @FXML private FlowPane fpProductContainer;
    @FXML private TextField txtSearch;

    // Khai báo tập trung toàn bộ DAO ở đây
    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = ProductManager.getInstance().getActiveProducts();
        displayProducts(products);
    }

    private void displayProducts(List<Product> products) {
        fpProductContainer.getChildren().clear();

        for (Product p : products) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            card.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15; -fx-background-color: white;");
            card.setPrefWidth(220);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(180);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);

            String path = p.getImagePath();
            if (path != null && !path.isEmpty()) {
                try {
                    imageView.setImage(new Image(path, true));
                } catch (Exception e) {
                    imageView.setImage(new Image("https://via.placeholder.com/150?text=Loi+Load+Anh"));
                }
            } else {
                imageView.setImage(new Image("https://via.placeholder.com/150?text=Chua+Co+Anh"));
            }

            Label lblName = new Label(p.getName());
            lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Label lblPrice = new Label(String.format("%,.0f VNĐ", p.getCurrentPrice()));
            lblPrice.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 14px;");

            Button btnBid = new Button("Vào Đấu Giá");
            btnBid.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
            btnBid.setOnAction(e -> {
                ProductManager.getInstance().setSelectedProductId(p.getId());
                SceneManager.getInstance().switchScene("auction.fxml");
            });

            card.getChildren().addAll(imageView, lblName, lblPrice, btnBid);
            fpProductContainer.getChildren().add(card);
        }
    }

    @FXML
    public void handleSearch() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadProducts();
            return;
        }
        List<Product> filteredList = productDAO.searchActiveProducts(keyword);
        displayProducts(filteredList);
    }

    // TÍNH NĂNG MỚI: Bật Popup thông báo khi nhấn vào chuông
    @FXML
    public void handleShowNotifications() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) return;

        List<Product> pendingOrders = productDAO.getPendingDeliveries(currentUser.getId());

        Stage popupStage = new Stage();
        popupStage.setTitle("Thông báo đơn hàng");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-background-color: white;");
        layout.setPrefWidth(350);

        if (pendingOrders.isEmpty()) {
            layout.getChildren().add(new Label("Bạn không có thông báo mới."));
        } else {
            for (Product order : pendingOrders) {
                VBox card = new VBox(5);
                card.setStyle("-fx-border-color: #f39c12; -fx-padding: 10; -fx-background-color: #fff8e7; -fx-border-radius: 5;");

                Label lblName = new Label("🎁 Sản phẩm: " + order.getName());
                lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                Label lblPrice = new Label("Đã thanh toán: " + String.format("%,.0f VNĐ", order.getCurrentPrice()));
                lblPrice.setStyle("-fx-text-fill: #e74c3c;");

                Button btnReceive = new Button("Đã nhận được hàng");
                btnReceive.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

                btnReceive.setOnAction(e -> {
                    // Mẹo chống bug click đúp: Khóa nút ngay khi vừa ấn vào
                    btnReceive.setDisable(true);

                    boolean moneySent = userDAO.addBalance(order.getSellerId(), order.getCurrentPrice());
                    if (moneySent) {
                        productDAO.updateProductStatus(order.getId(), "Đã hoàn thành");
                        SceneManager.getInstance().showPopup("Thành công", "Đã xác nhận nhận hàng!");
                        popupStage.close();
                    } else {
                        // Chuyển tiền lỗi thì nhả nút ra cho ấn lại
                        btnReceive.setDisable(false);
                        SceneManager.getInstance().showPopup("Lỗi", "Vui lòng xác nhận lại.");
                    }
                });

                card.getChildren().addAll(lblName, lblPrice, btnReceive);
                layout.getChildren().add(card);
            }
        }

        Scene scene = new Scene(layout);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @FXML
    public void handleGoToSeller() {
        SceneManager.getInstance().switchScene("seller.fxml");
    }

    @FXML
    public void handleLogout() {
        SessionManager.getInstance().logout();
        SceneManager.getInstance().switchScene("login.fxml");
    }

    @FXML
    public void handleGoToProfile() {
        SceneManager.getInstance().switchScene("profile.fxml");
    }
}