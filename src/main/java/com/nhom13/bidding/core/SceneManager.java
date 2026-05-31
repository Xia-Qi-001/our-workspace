package com.nhom13.bidding.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;

    private SceneManager() {}

    public static synchronized SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setMainStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void switchScene(String fxmlFile) {
        try {
            // Định vị chính xác tài nguyên FXML từ resources
            String path = "/" + fxmlFile;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            showPopup("Lỗi Kiến Trúc", "Không thể map tệp giao diện: " + fxmlFile);
            e.printStackTrace();
        }
    }

    public void showPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}