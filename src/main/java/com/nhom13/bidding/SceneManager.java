package com.nhom13.bidding;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * SceneManager - Singleton điều hướng màn hình trong ứng dụng JavaFX.
 *
 * Quản lý Stage chính và thực hiện chuyển cảnh bằng cách load FXML,
 * đảm bảo KHÔNG dùng new Stage().show() mà luôn dùng Stage hiện có.
 *
 * Cách sử dụng:
 *   // Khởi tạo (trong Application.start())
 *   SceneManager.getInstance().setStage(primaryStage);
 *
 *   // Chuyển cảnh (trong Controller)
 *   SceneManager.getInstance().switchScene("HomeView.fxml");
 */
public class SceneManager {

    // --- Singleton Pattern ---
    private static SceneManager instance;

    private Stage primaryStage;

    private SceneManager() {
        // Private constructor ngăn khởi tạo từ bên ngoài
    }

    /**
     * Lấy instance duy nhất của SceneManager.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // --- Quản lý Stage ---

    /**
     * Thiết lập Stage chính của ứng dụng.
     * Phải được gọi một lần trong phương thức Application.start().
     *
     * @param stage Stage chính (primaryStage)
     */
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Lấy Stage chính.
     *
     * @return Stage hiện tại
     */
    public Stage getStage() {
        return primaryStage;
    }

    // --- Điều hướng ---

    /**
     * Chuyển cảnh bằng cách load file FXML và set Scene mới lên Stage hiện có.
     *
     * FXML được load từ resources cùng package (com/nhom13/bidding/).
     * Không tạo Stage mới — tuân thủ nguyên tắc dùng SceneManager cho mọi chuyển cảnh.
     *
     * @param fxmlFile Tên file FXML cần load (ví dụ: "HomeView.fxml")
     */
    public void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Lỗi: Không thể load FXML file '" + fxmlFile + "'");
            e.printStackTrace();
        }
    }
}
