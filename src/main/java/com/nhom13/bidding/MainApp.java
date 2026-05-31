package com.nhom13.bidding;

import com.nhom13.bidding.core.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * MainApp - Cửa ngõ khởi chạy ứng dụng JavaFX.
 * Nằm ở package gốc để dễ quản lý và quét tài nguyên.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Thiết lập tiêu đề cho cửa sổ chính
        primaryStage.setTitle("Sàn Đấu Giá Nhóm 13 - v3");

        // 2. Bàn giao cửa sổ gốc Stage cho SceneManager độc quyền kiểm soát
        SceneManager.getInstance().setMainStage(primaryStage);

        // 3. Gọi màn hình mở màn (File login.fxml nằm trong resources/.../view/)
        SceneManager.getInstance().switchScene("login.fxml");
    }

    @Override
    public void stop() throws Exception {
        // Luôn ép chết các luồng chạy ngầm (đếm ngược LiveAuction) khi tắt app bằng dấu X
        System.out.println("Hệ thống đang dọn dẹp Thread ngầm...");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}