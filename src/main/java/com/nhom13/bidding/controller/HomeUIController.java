package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.LiveAuctionController;
import com.nhom13.bidding.core.ProductManager;
import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeUIController {

    @FXML private ListView<Product> listProducts;
    @FXML private TextField txtBidAmount;

    @FXML
    public void initialize() {
        refreshList();

        // LUỒNG QUAN TRỌNG: Nhấp vào vật phẩm trên sàn -> Vào sàn đấu giá riêng
        listProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // 1. Lưu ID vật phẩm đã chọn vào kho trung chuyển của Huy
                ProductManager.getInstance().setSelectedProductId(newVal.getId());
                // 2. Chuyển cảnh sang phòng đấu giá chi tiết
                SceneManager.getInstance().switchScene("auction.fxml");
            }
        });
    }

    private void refreshList() {
        ObservableList<Product> products = FXCollections.observableArrayList(ProductManager.getInstance().getActiveProducts());
        listProducts.setItems(products);

        listProducts.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " | Giá: " + String.format("%,.0f VNĐ", item.getCurrentPrice()));
                }
            }
        });
    }

    @FXML
    public void handlePlaceBidClick() {
        Product selected = listProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneManager.getInstance().showPopup("Lỗi", "Cậu chưa chọn món đồ nào để Bid cả!");
            return;
        }

        try {
            double amount = Double.parseDouble(txtBidAmount.getText().trim());
            LiveAuctionController core = new LiveAuctionController(selected);
            core.processBid(amount);
            txtBidAmount.clear();
            refreshList();
        } catch (Exception e) {
            SceneManager.getInstance().showPopup("Lỗi", "Số tiền không hợp lệ!");
        }
    }

    @FXML public void handleGoToProfile() { SceneManager.getInstance().switchScene("profile.fxml"); }
    @FXML public void handleGoToSeller() { SceneManager.getInstance().switchScene("seller.fxml"); }
    @FXML public void handleLogout() { SceneManager.getInstance().switchScene("login.fxml"); }
}