# ☕ [Phát triển hệ thống đấu giá trực tuyến] - Nhóm 13.

## 👥 Thành viên nhóm
| MSSV | Họ và tên | Nhiệm vụ |
| :--- | :--- | :--- |
| 25020038 | Lê Hữu Bằng | Leader |
| 25020182 | Nguyễn Nhất Huy |  |
| 25020159 | Dương Bá Việt Hoàng | |

## 📊 1. Tổng quan tiến độ (Cập nhật: 31/03/2026)
**Core Engine:** 0/6 Module hoàn thành
![Progress Phase 1](https://progress-bar.dev/0/?scale=6&title=Completed&width=400&color=e74c3c)

**Advanced Implementation:** 0/7 Module hoàn thành
![Progress Phase 2](https://progress-bar.dev/0/?scale=7&title=Completed&width=400&color=f39c12)

---

## ⚖️ 2. Luật Mở Khóa & Phân Phối Điểm (% Cố định)

### 🔒 2.1. Dependency Lock (Khóa phụ thuộc)
* **Tuyệt đối KHÔNG triển khai Advanced Features khi Core Engine chưa hoàn thiện 100%.**
* **Hình phạt:** Mọi điểm đóng góp từ các task Phase 2 sẽ bị **ĐÓNG BĂNG** (không ghi nhận) cho đến khi toàn bộ Phase 1 được Leader nghiệm thu.

### 🤝 2.2. Cơ chế Hỗ trợ (Assist) & Review qua GitHub
* **Xác nhận hỗ trợ:** Thành viên chỉ được sửa code người khác khi đã tạo 1 **GitHub Issue**, tag tên người hỗ trợ và được xác nhận (Comment "Chốt" hoặc "Đồng ý") trong Issue đó.
* **Chia sẻ quyền lợi:** Nếu hỗ trợ fix logic quan trọng (>30% module), người hỗ trợ hưởng **50% số điểm** task đó (trừ trực tiếp từ người nhận task chính).
* **Leader Review:** Nếu Pull Request (PR) có lỗi, Leader trả bài. Tự sửa thành công -> 100% điểm. Nếu để Leader phải can thiệp sửa hộ để chạy được -> **Trừ 50% điểm** task đó chuyển cho Leader.

---

## 📦 3. Danh sách Module & Claim Nhiệm vụ (Tổng 110%)

### 🛠️ Nhóm 1: Core Engine (40% - Ưu tiên số 1)
- [ ] **Quản lý User & Auth (Rank B - 5%)**
  - *Mô tả:* Đăng ký, đăng nhập, phân quyền Admin/Seller/Bidder.
  - *Phụ trách:* `[Trống]`
- [ ] **Quản lý Sản phẩm (Rank B - 5%)**
  - *Mô tả:* CRUD sản phẩm (Thêm/Sửa/Xóa), quản lý danh mục hàng hóa của Seller.
  - *Phụ trách:* `[Trống]`
- [ ] **Bidding Core Logic (Rank S - 15%)**
  - *Mô tả:* Xử lý đặt giá, kiểm tra bước giá hợp lệ, cập nhật người dẫn đầu.
  - *Phụ trách:* `[Trống]`
- [ ] **UI/UX Framework (Rank C - 5%)**
  - *Mô tả:* Thiết kế layout JavaFX, hệ thống menu điều hướng và phối màu.
  - *Phụ trách:* `[Trống]`
- [ ] **Auction Lifecycle (Rank A - 8%)**
  - *Mô tả:* Tự động đóng phiên khi hết giờ, xác định người thắng và chuyển trạng thái đơn hàng.
  - *Phụ trách:* `[Trống]`
- [ ] **Exception Handling (Rank C - 2%)**
  - *Mô tả:* Bắt lỗi nhập liệu, lỗi kết nối cơ bản, đảm bảo ứng dụng không crash.
  - *Phụ trách:* `[Trống]`

### 🚀 Nhóm 2: Advanced Features & Architecture (50%)
- [ ] **Concurrency Control (Rank SS - 20%)**
  - *Mô tả:* Giải quyết Race Condition (tranh chấp) khi nhiều người cùng đặt giá vào giây cuối.
  - *Phụ trách:* `[Trống]`
- [ ] **Distributed Architecture (Rank S - 10%)**
  - *Mô tả:* Thiết kế mô hình Client-Server, giao tiếp qua Socket hoặc REST API.
  - *Phụ trách:* `[Trống]`
- [ ] **Realtime Communication (Rank S - 10%)**
  - *Mô tả:* Áp dụng Observer Pattern để cập nhật giá ngay lập tức cho tất cả các Client.
  - *Phụ trách:* `[Trống]`
- [ ] **Structural Design Patterns (Rank S - 10%)**
  - *Mô tả:* Triển khai tối thiểu 4 patterns: Singleton, Factory, Strategy, Observer.
  - *Phụ trách:* `[Trống]`

### 🧪 Nhóm 3: Infrastructure & Optimization (20% Bonus)
- [ ] **Quality Assurance (Rank A - 10%)**
  - *Mô tả:* Viết Unit Test (JUnit), thiết lập CI/CD (GitHub Actions) để tự động kiểm tra mã nguồn.
  - *Phụ trách:* `[Trống]`
- [ ] **Auto-bid Logic (Rank B - 5%)**
  - *Mô tả:* Thuật toán tự động nâng giá cho người dùng đến mức trần thiết lập.
  - *Phụ trách:* `[Trống]`
- [ ] **Anti-sniping & Chart (Rank B - 5%)**
  - *Mô tả:* Tự gia hạn phiên đấu giá giây cuối và vẽ biểu đồ lịch sử giá trực quan.
  - *Phụ trách:* `[Trống]`

---
*Ghi chú: Bài tập lớn chiếm trọng số ~30-40% tổng điểm môn học. Anh em tập trung claim task và triển khai đúng tiến độ.*
