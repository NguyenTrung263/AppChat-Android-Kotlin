package com.example.appchat.view.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.databinding.ActivityChatBinding // Đảm bảo tên này khớp với file activity_chat2.xml
import com.example.appchat.view.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("receiverEmail") ?: "Chat"

        // Lấy thông tin người nhận từ Intent
        val receiverId = intent.getStringExtra("receiverId") ?: ""
        val receiverEmail = intent.getStringExtra("receiverEmail") ?: "Chat"

        // Hiển thị email người nhận lên thanh tiêu đề
        supportActionBar?.title = receiverEmail
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Thiết lập RecyclerView (Dùng ID 'recyclerview' từ XML của bạn)
        adapter = ChatAdapter(emptyList())
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        // Bắt đầu lắng nghe tin nhắn
        viewModel.startChat(receiverId)

        // Theo dõi tin nhắn mới
        viewModel.messages.observe(this) { list ->
            adapter.update(list)
            if (list.isNotEmpty()) {
                binding.recyclerview.scrollToPosition(list.size - 1)
            }
        }

        // Xử lý nút gửi (Dùng ID 'btnSend' và 'etMessage')
        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.send(receiverId, text)
                binding.etMessage.text.clear()
            }
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // Nút quay lại trên thanh Toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}