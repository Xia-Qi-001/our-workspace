# ☕ [Phát triển hệ thống đấu giá trực tuyến] - Nhóm 13.

## 👥 2. Thành viên nhóm & Chỉ số đóng góp (KPI)

| MSSV | Họ và tên | Vai trò chuyên môn | % commit | Rank / Trạng thái |
| :--- | :--- | :--- | :---: | :---: | :--- |
| 25020038 | **Lê Hữu Bằng** | **Leader** |  |  |
| 25020182 | **Nguyễn Nhất Huy** | `[Tự điền]` |  |  |
| 25020159 | **Dương Bá Việt Hoàng** | `[Tự điền]` |  |  |

## 📖 1. Giới thiệu dự án
Hệ thống đấu giá trực tuyến là nền tảng phần mềm cho phép nhiều người dùng cùng tham gia cạnh tranh giá để mua một sản phẩm trong một khoảng thời gian xác định. Dự án được phát triển bằng ngôn ngữ Java.

## ⚠️ 2. Quy định làm việc nhóm (Quy tắc sống còn)
* **Commit code:** Phải commit mã nguồn thường xuyên lên GitHub để chứng minh tiến độ; không chấp nhận trường hợp chỉ có một commit duy nhất vào thời điểm cuối.
* **Trách nhiệm giải trình:** Nếu bất kỳ thành viên nào không hiểu hoặc không thể giải thích bất kỳ phần mã nguồn nào, toàn bộ nhóm sẽ bị chấm 0 điểm.
* **Điểm số:** Chấm điểm theo nhóm. Nhóm tự phân chia điểm theo mức độ đóng góp, tổng điểm cá nhân bằng điểm chung của nhóm.

## 📊 3. Tổng quan tiến độ (Cập nhật: 31/03/2026)

**Core Engine:** 0/6 Module hoàn thành
![Progress](https://img.shields.io/badge/Progress-0%2F6-red?style=for-the-badge)

**Advanced Implementation:** 0/7 Module hoàn thành
![Progress](https://img.shields.io/badge/Progress-0%2F7-orange?style=for-the-badge)

## ⚖️ 4. Luật Mở Khóa & Phân Phối Điểm (% Cố định)

### 🔒 4.1. Dependency Lock (Khóa phụ thuộc)
* **Tuyệt đối KHÔNG triển khai Advanced Features khi Core Engine chưa hoàn thiện 100%.**
* **Hình phạt:** Mọi điểm đóng góp từ các task Phase 2 sẽ bị **ĐÓNG BĂNG** (không ghi nhận) cho đến khi toàn bộ Phase 1 được Leader nghiệm thu.

### 🤝 4.2. Cơ chế Hỗ trợ (Assist) & Review qua GitHub
* **Xác nhận hỗ trợ:** Thành viên chỉ được sửa code người khác khi đã tạo 1 **GitHub Issue**, tag tên người hỗ trợ và được xác nhận (Comment "Chốt" hoặc "Đồng ý") trong Issue đó.
* **Chia sẻ quyền lợi:** Nếu hỗ trợ fix logic quan trọng (>30% module), người hỗ trợ hưởng **50% số điểm** task đó (trừ trực tiếp từ người nhận task chính).
* **Leader Review:** Nếu Pull Request (PR) có lỗi, Leader trả bài. Tự sửa thành công -> 100% điểm. Nếu để Leader phải can thiệp sửa hộ để chạy được -> **Trừ 50% điểm** task đó chuyển cho Leader.

---

## 📦 5. Phân công công việc (Phase 1 - Bắt buộc)

### Gói 1: Quản lý Tài khoản & Sản phẩm
- [ ] **Xây dựng chức năng quản lý người dùng** (Rank C - 2%)
  - *Mô tả:* Đăng ký, đăng nhập. Phân quyền Admin, Seller, Bidder.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Cài đặt chức năng quản lý sản phẩm** (Rank B - 5%)
  - *Mô tả:* Seller thêm, sửa, xóa thông tin sản phẩm (Tên, mô tả, giá, thời gian).
  - *Người phụ trách:* `[Điền tên]`

### Gói 2: Giao diện người dùng (GUI)
- [ ] **Thiết kế các màn hình chính** (Rank D - 2%)
  - *Mô tả:* Vẽ giao diện JavaFX/Swing: Danh sách, chi tiết, quản lý, và phòng đấu giá.
  - *Người phụ trách:* `[Điền tên]`

### Gói 3: Core Logic Đấu giá
- [ ] **Xử lý luồng tham gia đấu giá** (Rank S - 15%)
  - *Mô tả:* Nhận giá đặt, kiểm tra tính hợp lệ và cập nhật người dẫn đầu.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Xử lý luồng kết thúc phiên** (Rank A - 10%)
  - *Mô tả:* Đóng phiên khi hết giờ, chốt người thắng, chuyển trạng thái.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Xử lý lỗi & ngoại lệ** (Rank C - 2%)
  - *Mô tả:* Chặn đặt giá thấp hơn giá hiện tại, đấu giá khi đã đóng phiên, lỗi kết nối.
  - *Người phụ trách:* `[Điền tên]`

---

## 📦 6. Phân công công việc (Phase 2 - Nâng cao)
*(Chỉ mở khóa khi Phase 1 đạt 100%)*

### Gói 4: Chức năng nâng cao
- [ ] **Xử lý đấu giá đồng thời (Concurrent)** (Rank SS - 25%)
  - *Mô tả:* Tránh lost update, race condition khi nhiều người cùng đặt giá.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Realtime Update** (Rank S - 15%)
  - *Mô tả:* Cập nhật giá ngay lập tức cho toàn bộ client (Observer/Socket).
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Auto-Bidding** (Rank A - 10%)
  - *Mô tả:* Tự trả giá thay người dùng dựa trên maxBid và increment.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Gia hạn phiên đấu giá (Anti-sniping)** (Rank B - 5%)
  - *Mô tả:* Cộng thêm thời gian nếu có lượt đặt giá ở những giây cuối.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Bid History Visualization** (Rank B - 5%)
  - *Mô tả:* Biểu đồ đường (line chart) lịch sử giá đấu realtime.
  - *Người phụ trách:* `[Điền tên]`

### Gói 5: Kiến trúc & Triển khai
- [ ] **Kiến trúc Client-Server & MVC** (Rank SS - 25%)
  - *Mô tả:* Tách biệt Client/Server, giao tiếp REST API/Socket. Đảm bảo luồng dữ liệu chuẩn.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Áp dụng Design Pattern** (Rank S - 15%)
  - *Mô tả:* Singleton, Factory Method, Observer, Strategy.
  - *Người phụ trách:* `[Điền tên]`

- [ ] **Tích hợp & Unit Test** (Rank A - 10%)
  - *Mô tả:* Cấu hình Maven/Gradle, viết JUnit, CI/CD.
  - *Người phụ trách:* `[Điền tên]`
---
*Ghi chú: Bài tập lớn chiếm trọng số ~30% tổng điểm môn học. Anh em tập trung claim task và triển khai đúng tiến độ.*
