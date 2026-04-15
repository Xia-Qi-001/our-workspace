package com.nhom13.bidding;

/**
 * Exceptions - Lớp chứa tất cả các ngoại lệ tùy chỉnh cho hệ thống đấu giá.
 *
 * Gom các Custom Exception vào một file duy nhất để dễ quản lý và bảo trì.
 * Mỗi ngoại lệ được khai báo dưới dạng static inner class, kế thừa từ Exception
 * (Checked Exception) để buộc lập trình viên phải xử lý tại nơi gọi.
 *
 * Cách sử dụng:
 *   throw new Exceptions.InvalidBidException("...");
 *   throw new Exceptions.AuctionClosedException("...");
 *   throw new Exceptions.UnauthorizedActionException("...");
 */
public class Exceptions {

    /**
     * InvalidBidException - Ngoại lệ khi giá đặt không hợp lệ.
     *
     * Được ném ra khi người dùng đặt giá (bid) không hợp lệ,
     * cụ thể là khi giá đặt thấp hơn mức giá tối thiểu yêu cầu
     * (currentPrice + bidIncrement).
     */
    public static class InvalidBidException extends Exception {

        /**
         * Khởi tạo ngoại lệ với thông báo lỗi chi tiết.
         *
         * @param message Thông báo mô tả lý do giá đặt không hợp lệ
         */
        public InvalidBidException(String message) {
            super(message);
        }
    }

    /**
     * AuctionClosedException - Ngoại lệ khi phiên đấu giá đã đóng.
     *
     * Được ném ra khi người dùng cố gắng đặt giá cho một phiên đấu giá
     * đã đóng. Phiên đấu giá được coi là đóng khi:
     *   1. Sản phẩm chưa được Admin duyệt (approved == false), HOẶC
     *   2. Thời gian đấu giá đã hết (System.currentTimeMillis() >= endTime).
     */
    public static class AuctionClosedException extends Exception {

        /**
         * Khởi tạo ngoại lệ với thông báo lỗi chi tiết.
         *
         * @param message Thông báo mô tả lý do phiên đấu giá đã đóng
         */
        public AuctionClosedException(String message) {
            super(message);
        }
    }

    /**
     * UnauthorizedActionException - Ngoại lệ khi hành động không được phép.
     *
     * Được ném ra khi người dùng thực hiện hành động mà họ không có quyền.
     * Ví dụ điển hình: Seller (người bán) tự đặt giá (bid) cho sản phẩm
     * của chính mình — đây là hành vi không được phép trong hệ thống.
     */
    public static class UnauthorizedActionException extends Exception {

        /**
         * Khởi tạo ngoại lệ với thông báo lỗi chi tiết.
         *
         * @param message Thông báo mô tả hành động trái phép
         */
        public UnauthorizedActionException(String message) {
            super(message);
        }
    }
}