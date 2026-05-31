package com.nhom13.bidding.controller;

import com.nhom13.bidding.core.SceneManager;
import com.nhom13.bidding.core.SessionManager;
import com.nhom13.bidding.dao.ProductDAO;
import com.nhom13.bidding.model.Product;
import com.nhom13.bidding.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SellerUIController {

    @FXML private TextField txtProductName;
    @FXML private TextField txtStartPrice;
    @FXML private TextField txtStepPrice;
    @FXML private TextField txtImagePath; // Biến text hiển thị đường dẫn ảnh
    @FXML private DatePicker dpEndDate;
    @FXML private ComboBox<String> cbHour;
    @FXML private ComboBox<String> cbMinute;

    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
        for (int i = 0; i < 24; i++) cbHour.getItems().add(String.format("%02d", i));
        for (int i = 0; i < 60; i++) cbMinute.getItems().add(String.format("%02d", i));
        cbHour.getSelectionModel().select("23");
        cbMinute.getSelectionModel().select("59");
    }

    // Hàm gọi cửa sổ chọn file từ máy tính
    @FXML
    public void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh sản phẩm");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Định dạng ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Bật cửa sổ duyệt file của Windows/MacOS
        File selectedFile = fileChooser.showOpenDialog(txtProductName.getScene().getWindow());

        if (selectedFile != null) {
            // Biến đổi đường dẫn ổ đĩa thông thường thành chuỗi URI để JavaFX hiểu được
            txtImagePath.setText(selectedFile.toURI().toString());
        }
    }

    @FXML
    public void handlePostProduct() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            SceneManager.getInstance().showPopup("Lỗi", "Vui lòng đăng nhập lại!");
            return;
        }

        try {
            String name = txtProductName.getText().trim();
            double startPrice = Double.parseDouble(txtStartPrice.getText().trim());
            double stepPrice = Double.parseDouble(txtStepPrice.getText().trim());
            String imagePath = txtImagePath.getText().trim();

            LocalDate date = dpEndDate.getValue();
            String hour = cbHour.getValue();
            String minute = cbMinute.getValue();

            if (name.isEmpty() || startPrice <= 0 || stepPrice <= 0 || date == null) {
                SceneManager.getInstance().showPopup("Lỗi", "Vui lòng điền đầy đủ các trường bắt buộc!");
                return;
            }

            LocalTime time = LocalTime.parse(hour + ":" + minute + ":00");
            LocalDateTime endTime = LocalDateTime.of(date, time);

            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setStartPrice(startPrice);
            newProduct.setStepPrice(stepPrice);
            newProduct.setSellerId(currentUser.getId());
            newProduct.setEndTime(endTime);
            newProduct.setStatus("Đang đấu giá");
            newProduct.setImagePath(imagePath); // Gán thông tin ảnh vào model

            if (productDAO.addProduct(newProduct)) {
                SceneManager.getInstance().showPopup("Thành công", "Sản phẩm kèm ảnh đã được đẩy lên hệ thống!");
                SceneManager.getInstance().switchScene("home.fxml");
            } else {
                SceneManager.getInstance().showPopup("Lỗi", "Lỗi đồng bộ dữ liệu với máy chủ.");
            }

        } catch (NumberFormatException e) {
            SceneManager.getInstance().showPopup("Lỗi", "Định dạng giá tiền không hợp lệ!");
        }
    }

    @FXML
    public void handleBackToHome() {
        SceneManager.getInstance().switchScene("home.fxml");
    }
}