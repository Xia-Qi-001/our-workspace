package com.nhom13.bidding;

import com.nhom13.bidding.Exceptions;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * BidController - Lớp Controller mẫu xử lý sự kiện đặt giá trong giao diện JavaFX.
 *
 * Lớp này minh họa cách sử dụng try-catch để bắt các ngoại lệ tùy chỉnh
 * (Custom Exceptions) và hiển thị thông báo lỗi ra màn hình người dùng
 * thông qua JavaFX Alert.
 *
 * Luồng xử lý:
 *   1. Người dùng nhập giá đặt và nhấn nút "Đặt giá".
 *   2. Controller gọi item.placeBid(...).
 *   3. Nếu có lỗi, hiển thị Alert tương ứng loại ngoại lệ.
 *   4. Nếu thành công, hiển thị Alert thông báo thành công.
 */
public class BidController {

    /**
     * Xử lý sự kiện khi người dùng nhấn nút "Đặt giá".
     *
     * Phương thức này lấy dữ liệu từ giao diện, gọi placeBid(),
     * và xử lý tất cả các ngoại lệ có thể xảy ra.
     *
     * @param item      Sản phẩm đang được đấu giá
     * @param newBid    Giá mà người dùng muốn đặt (lấy từ TextField)
     * @param bidderId  ID của người đang đặt giá (lấy từ session đăng nhập)
     */
    public void handlePlaceBid(Item item, double newBid, String bidderId) {
        try {
            // Gọi phương thức placeBid — có thể ném ra 3 loại ngoại lệ
            item.placeBid(newBid, bidderId);

            // Nếu không có ngoại lệ nào được ném ra => đặt giá thành công
            showAlert(AlertType.INFORMATION,
                    "Thành công",
                    "Đặt giá thành công!",
                    "Bạn đã đặt giá " + newBid + " cho sản phẩm " + item.getName() + ".\n"
                            + "Bạn hiện đang là người trả giá cao nhất.");

        } catch (Exceptions.UnauthorizedActionException e) {
            // Trường hợp: Seller cố tự bid sản phẩm của mình
            // => Hiển thị Alert dạng WARNING (cảnh báo vi phạm quyền hạn)
            showAlert(AlertType.WARNING,
                    "Không có quyền",
                    "Bạn không được phép thực hiện hành động này!",
                    e.getMessage());

        } catch (Exceptions.AuctionClosedException e) {
            // Trường hợp: Phiên đấu giá đã đóng (chưa duyệt hoặc hết hạn)
            // => Hiển thị Alert dạng ERROR (phiên đấu giá không còn hoạt động)
            showAlert(AlertType.ERROR,
                    "Phiên đấu giá đã đóng",
                    "Không thể đặt giá!",
                    e.getMessage());

        } catch (Exceptions.InvalidBidException e) {
            // Trường hợp: Giá đặt không đạt mức tối thiểu (currentPrice + bidIncrement)
            // => Hiển thị Alert dạng WARNING (hướng dẫn người dùng nhập giá hợp lệ)
            showAlert(AlertType.WARNING,
                    "Giá đặt không hợp lệ",
                    "Giá bạn đặt chưa đủ mức tối thiểu!",
                    e.getMessage());
        }
    }

    /**
     * Phương thức tiện ích để hiển thị Alert trên giao diện JavaFX.
     *
     * Phương thức này tạo một hộp thoại thông báo (Alert) với đầy đủ thông tin:
     * tiêu đề, header, và nội dung chi tiết, sau đó hiển thị và chờ người dùng đóng.
     *
     * @param alertType  Loại Alert (INFORMATION, WARNING, ERROR, ...)
     * @param title      Tiêu đề cửa sổ Alert
     * @param header     Dòng tiêu đề chính trong Alert
     * @param content    Nội dung chi tiết của thông báo
     */
    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // Hiển thị Alert và chờ người dùng nhấn OK
    }
}
