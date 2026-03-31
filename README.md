# ☕ [Phát triển hệ thống đấu giá trực tuyến] - Nhóm 13.

## 👥 Thành viên nhóm
| MSSV | Họ và tên | Nhiệm vụ |
| :--- | :--- | :--- |
| 25020038 | Lê Hữu Bằng | Leader |
| 25020182 | Nguyễn Nhất Huy |  |
| 25020159 | Dương Bá Việt Hoàng | |

## 📖 1. Giới thiệu dự án
Hệ thống đấu giá trực tuyến là nền tảng phần mềm cho phép nhiều người dùng cùng tham gia cạnh tranh giá để mua một sản phẩm trong một khoảng thời gian xác định. Dự án được phát triển bằng ngôn ngữ Java.

## ⚠️ 2. Quy định làm việc nhóm (Quy tắc sống còn)
* **Commit code:** Phải commit mã nguồn thường xuyên lên GitHub để chứng minh tiến độ; không chấp nhận trường hợp chỉ có một commit duy nhất vào thời điểm cuối.
* **Trách nhiệm giải trình:** Nếu bất kỳ thành viên nào không hiểu hoặc không thể giải thích bất kỳ phần mã nguồn nào, toàn bộ nhóm sẽ bị chấm 0 điểm.
* **Điểm số:** Chấm điểm theo nhóm. Nhóm tự phân chia điểm theo mức độ đóng góp, tổng điểm cá nhân bằng điểm chung của nhóm.
* **Công cụ hỗ trợ:** Được phép sử dụng AI, Google, GitHub nhưng bài nộp là sản phẩm chung và cả nhóm chịu trách nhiệm hoàn toàn về nội dung.

## 📦 3. Tiến độ & Phân công công việc (Phase 1 - Bắt buộc)

### Gói 1: Quản lý Tài khoản & Sản phẩm
- [ ] **Xây dựng chức năng quản lý người dùng**
  - *Mô tả:* Cài đặt luồng đăng ký, đăng nhập. Phân quyền rõ ràng cho 3 vai trò: Admin, Seller, Bidder.
  - *Độ khó:* Rank C
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Cài đặt chức năng quản lý sản phẩm**
  - *Mô tả:* Cho phép Seller thêm, sửa, xóa thông tin sản phẩm (Tên, mô tả, giá khởi điểm, giá hiện tại, thời gian).
  - *Độ khó:* Rank B
  - *Người phụ trách:* `[Điền tên]`

### Gói 2: Giao diện người dùng (GUI)
- [ ] **Khởi tạo và thiết kế các màn hình chính**
  - *Mô tả:* Dùng JavaFX hoặc Swing để vẽ giao diện: Danh sách phiên đấu giá, chi tiết sản phẩm, quản lý cho Seller, và màn hình đấu giá trực tiếp.
  - *Độ khó:* Rank D
  - *Người phụ trách:* `[Điền tên]`

### Gói 3: Core Logic Đấu giá
- [ ] **Xử lý luồng tham gia đấu giá (Đặt giá)**
  - *Mô tả:* Nhận giá đặt từ người dùng, kiểm tra tính hợp lệ (phải cao hơn giá hiện tại) và cập nhật người dẫn đầu.
  - *Độ khó:* Rank S
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Xử lý luồng kết thúc phiên**
  - *Mô tả:* Tự động đóng phiên khi hết giờ, xác định người thắng và chuyển trạng thái (OPEN -> RUNNING -> FINISHED -> PAID / CANCELED).
  - *Độ khó:* Rank A
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Xử lý lỗi & ngoại lệ**
  - *Mô tả:* Chặn các luồng thao tác lỗi như: đặt giá thấp hơn giá hiện tại, cố tình đấu giá khi phiên đã đóng, hoặc các lỗi dữ liệu/kết nối.
  - *Độ khó:* Rank C
  - *Người phụ trách:* `[Điền tên]`

## 📦 4. Tiến độ & Phân công công việc (Phase 2 - Nâng cao)

### Gói 4: Chức năng nâng cao
- [ ] **Xử lý đấu giá đồng thời (Concurrent Bidding)**
  - *Mô tả:* Giải quyết bài toán nhiều người đặt giá cùng một thời điểm, tránh lost update hoặc hai người cùng thắng.
  - *Độ khó:* Rank SS
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Realtime Update (Cập nhật thời gian thực)**
  - *Mô tả:* Khi có bid mới, toàn bộ client đang xem phải được cập nhật ngay lập tức mà không dùng polling.
  - *Độ khó:* Rank S
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Auto-Bidding (Đấu giá tự động)**
  - *Mô tả:* Hệ thống tự trả giá thay người dùng dựa trên giá tối đa (maxBid) và bước giá (increment).
  - *Độ khó:* Rank A
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Gia hạn phiên đấu giá (Anti-sniping)**
  - *Mô tả:* Tự động cộng thêm thời gian nếu có người đặt giá trong những giây cuối cùng.
  - *Độ khó:* Rank B
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Bid History Visualization**
  - *Mô tả:* Vẽ biểu đồ đường (line chart) hiển thị lịch sử giá đấu theo thời gian thực.
  - *Độ khó:* Rank B
  - *Người phụ trách:* `[Điền tên]`

### Gói 5: Kiến trúc & Triển khai
- [ ] **Thiết kế kiến trúc Client-Server & MVC**
  - *Mô tả:* Tách biệt Client (JavaFX) và Server (Controller -> Model -> DAO), giao tiếp qua REST API hoặc Socket.
  - *Độ khó:* Rank S+
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Áp dụng Design Pattern**
  - *Mô tả:* Triển khai các pattern: Singleton, Factory Method, Observer, Strategy.
  - *Độ khó:* Rank S
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Tích hợp & Unit Test**
  - *Mô tả:* Quản lý build bằng Maven/Gradle, viết Unit Test (JUnit), thiết lập CI/CD (GitHub Actions).
  - *Độ khó:* Rank A
  - *Người phụ trách:* `[Điền tên]`
