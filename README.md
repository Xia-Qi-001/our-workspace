# ☕ [Phát triển hệ thống đấu giá trực tuyến] - Nhóm 13.

## 👥 Thành viên nhóm
| MSSV | Họ và tên | Nhiệm vụ |
| :--- | :--- | :--- |
| 25020038 | Lê Hữu Bằng | Leader |
| 25020182 | Nguyễn Nhất Huy | |
| 25020159 | Dương Bá Việt Hoàng | |

## 📖 1. Giới thiệu dự án
[cite_start]Hệ thống đấu giá trực tuyến là nền tảng phần mềm cho phép nhiều người dùng cùng tham gia cạnh tranh giá để mua một sản phẩm trong một khoảng thời gian xác định [cite: 23-25]. [cite_start]Dự án được phát triển bằng ngôn ngữ Java[cite: 11].

## ⚠️ 2. Quy định làm việc nhóm (Quy tắc sống còn)
* [cite_start]**Commit code:** Phải commit mã nguồn thường xuyên lên GitHub để chứng minh tiến độ; không chấp nhận trường hợp chỉ có một commit duy nhất vào thời điểm cuối [cite: 19-20].
* [cite_start]**Trách nhiệm giải trình:** Nếu bất kỳ thành viên nào không hiểu hoặc không thể giải thích bất kỳ phần mã nguồn nào, toàn bộ nhóm sẽ bị chấm 0 điểm[cite: 21].
* [cite_start]**Điểm số:** Chấm điểm theo nhóm[cite: 15]. [cite_start]Nhóm tự phân chia điểm theo mức độ đóng góp, tổng điểm cá nhân bằng điểm chung của nhóm[cite: 15].
* [cite_start]**Công cụ hỗ trợ:** Được phép sử dụng AI, Google, GitHub nhưng bài nộp là sản phẩm chung và cả nhóm chịu trách nhiệm hoàn toàn về nội dung [cite: 17-18].

## 📦 3. Tiến độ & Phân công công việc (Phase 1)

### Gói 1: Quản lý Tài khoản & Sản phẩm 
- [ ] [cite_start]Xây dựng chức năng Đăng ký / đăng nhập tài khoản[cite: 33].
- [ ] [cite_start]Thiết lập 3 vai trò: Admin, Seller, Bidder [cite: 34-37].
- [ ] [cite_start]Cài đặt chức năng Thêm / sửa / xóa sản phẩm cho Seller[cite: 40].
- [ ] [cite_start]Quản lý thông tin: Tên, mô tả, giá khởi điểm, giá hiện tại, thời gian [cite: 41-45].

### Gói 2: Giao diện người dùng (GUI)
- [ ] [cite_start]Khởi tạo giao diện bằng JavaFX (khuyến nghị) hoặc Swing[cite: 63].
- [ ] [cite_start]Thiết kế màn hình danh sách phiên đấu giá[cite: 65].
- [ ] [cite_start]Thiết kế màn hình chi tiết sản phẩm và quản lý sản phẩm[cite: 66, 68].
- [ ] [cite_start]Thiết kế màn hình đấu giá trực tiếp (realtime bidding)[cite: 67].

### Gói 3: Core Logic Đấu giá
- [ ] [cite_start]Xử lý đặt giá: Kiểm tra tính hợp lệ của giá đấu và cập nhật người dẫn đầu [cite: 48-50].
- [ ] [cite_start]Xử lý kết thúc: Tự động đóng phiên khi hết thời gian và xác định người thắng cuộc [cite: 53-54].
- [ ] [cite_start]Chuyển trạng thái phiên: OPEN -> RUNNING -> FINISHED -> PAID / CANCELED[cite: 55].
- [ ] [cite_start]Xử lý ngoại lệ: Phát hiện lỗi đặt giá thấp hơn giá hiện tại hoặc đấu giá khi phiên đã đóng [cite: 58-59].
