package com.nhom13.bidding.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AuctionView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hệ Thống Đấu Giá Nhóm 13");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    // --- ĐÂY, THÊM CÁI HÀM NÀY VÀO ĐỂ FILE MAIN CÒN GỌI ĐƯỢC ---
    public static void main(String[] args) {
        launch(args);
    }
}