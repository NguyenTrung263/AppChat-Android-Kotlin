# AppChat - Android Kotlin Application

![Android Badge](https://img.shields.io/badge/Platform-Android-brightgreen)
![Kotlin Badge](https://img.shields.io/badge/Language-Kotlin-blue)
![Firebase Badge](https://img.shields.io/badge/Backend-Firebase-orange)

**AppChat** là một ứng dụng nhắn tin thời gian thực đơn giản được xây dựng trên nền tảng Android sử dụng ngôn ngữ Kotlin và Firebase. Dự án áp dụng mô hình kiến trúc MVVM để đảm bảo mã nguồn sạch và dễ bảo trì.

## 🚀 Tính năng chính
- **Đăng ký/Đăng nhập:** Quản lý người dùng qua Firebase Authentication.
- **Danh sách người dùng:** Hiển thị danh sách các tài khoản đã đăng ký trên hệ thống.
- **Nhắn tin thời gian thực:** Gửi và nhận tin nhắn tức thì qua Firebase Realtime Database.
- **Giao diện trực quan:** Phân biệt tin nhắn gửi đi (phải) và tin nhắn nhận về (trái).

## 🛠 Công nghệ sử dụng
- **Ngôn ngữ:** [Kotlin](https://kotlinlang.org/)
- **Kiến trúc:** MVVM (Model-View-ViewModel)
- **Database:** Firebase Realtime Database
- **Authentication:** Firebase Auth
- **UI Components:** View Binding, RecyclerView, Material Design, CardView.

## 📁 Cấu trúc thư mục chính
app/src/main/java/com/example/appchat/
├── 📂 data/                        # TẦNG DỮ LIỆU (DATA LAYER)
│   ├── 📂 model/                   # Định nghĩa các đối tượng dữ liệu
│   │   ├── ChatMessage.kt          # Cấu trúc tin nhắn (senderId, message, timestamp)
│   │   └── User.kt                 # Cấu trúc người dùng (uid, email)
│   └── 📂 repository/              # Nơi trực tiếp giao tiếp với Firebase
│       ├── AuthRepository.kt       # Xử lý Đăng ký, Đăng nhập, Đăng xuất
│       ├── UserRepository.kt       # Lấy danh sách người dùng từ Database
│       └── ChatRepository.kt       # Gửi và lắng nghe tin nhắn Realtime
│
├── 📂 view/                        # TẦNG GIAO DIỆN (UI LAYER)
│   ├── 📂 auth/                    # Các màn hình xác thực
│   │   ├── LoginActivity.kt        # Giao diện Đăng nhập & Đăng ký
│   │   └── AuthViewModel.kt        # Logic xử lý Auth, báo lỗi UI
│   ├── 📂 user/                    # Màn hình danh sách người dùng
│   │   ├── UsersActivity.kt        # Hiển thị danh sách bạn bè
│   │   └── UsersViewModel.kt       # Chuẩn bị dữ liệu cho danh sách
│   ├── 📂 chat/                    # Màn hình nhắn tin chi tiết
│   │   ├── ChatActivity.kt         # Giao diện khung chat, nút gửi
│   │   └── ChatViewModel.kt        # Xử lý luồng gửi/nhận tin nhắn
│   └── 📂 adapter/                 # Các bộ chuyển đổi cho RecyclerView
│       ├── UsersAdapter.kt         # Hiển thị từng dòng người dùng
│       └── ChatAdapter.kt          # Hiển thị tin nhắn (Trái/Phải)
│
└── 📂 utils/                       # Các hàm tiện ích dùng chung

🔄 Quy trình luân chuyển dữ liệu (Data Flow)

Quy trình này mô tả cách một tin nhắn được gửi từ thiết bị của bạn, lưu trữ trên đám mây và xuất hiện trên thiết bị của người nhận.

1. Giai đoạn Gửi dữ liệu (Input Phase)
View (ChatActivity): Người dùng nhập nội dung vào EditText và nhấn nút btnSend. View thu thập văn bản và ID người nhận, sau đó gọi hàm send() trong ViewModel.

ViewModel (ChatViewModel): Tiếp nhận dữ liệu, lấy thêm ID của người dùng hiện tại (Sender ID) từ FirebaseAuth và yêu cầu Repository thực hiện việc gửi.

Repository (ChatRepository): Tạo một đối tượng ChatMessage và đẩy lên đường dẫn tương ứng trên Firebase Realtime Database (ví dụ: chats/senderId_receiverId).

2. Giai đoạn Lưu trữ & Đồng bộ (Cloud Phase)
Firebase Realtime Database: Nhận yêu cầu push(), tự động tạo một mã định danh duy nhất (Push ID) cho tin nhắn đó và lưu trữ vĩnh viễn. Ngay khi dữ liệu được ghi thành công, Firebase phát một tín hiệu thay đổi đến tất cả các thiết bị đang "lắng nghe" (subscribe) vào đường dẫn này.

3. Giai đoạn Nhận & Hiển thị (Output Phase)
Repository: Nhờ hàm addValueEventListener, Repository nhận được một DataSnapshot (ảnh chụp dữ liệu) mới nhất từ Firebase. Nó chuyển đổi các bản ghi này thành một List<ChatMessage> và gửi ngược lên thông qua một Callback.

ViewModel: Nhận danh sách tin nhắn từ Repository và cập nhật vào biến MutableLiveData.

View (ChatActivity): Vì View đang "quan sát" (observe) biến LiveData này, nó sẽ ngay lập tức nhận được danh sách mới. View ra lệnh cho Adapter cập nhật dữ liệu.

Adapter (ChatAdapter): So sánh senderId trong từng tin nhắn với UID của mình để quyết định hiển thị bong bóng chat bên trái hay bên phải, sau đó vẽ lại giao diện trên RecyclerView.

---
© 2026 - Phát triển bởi **Nguyễn Thành Trung**
