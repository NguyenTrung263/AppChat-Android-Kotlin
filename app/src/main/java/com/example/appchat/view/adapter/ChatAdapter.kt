package com.example.appchat.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.data.model.ChatMessage
import com.example.appchat.databinding.ItemChatReceivedBinding
import com.example.appchat.databinding.ItemChatSentBinding
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(private var list: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Định nghĩa hằng số cho 2 loại View Type
    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    // Lấy UID của chính mình
    private val currentUid = FirebaseAuth.getInstance().uid

    // Xác định loại tin nhắn (Gửi hay Nhận) dựa trên senderId
    override fun getItemViewType(position: Int): Int {
        return if (list[position].senderId == currentUid) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    // Khởi tạo ViewHolder tương ứng với View Type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemChatSentBinding.inflate(inflater, parent, false)
            SentViewHolder(binding)
        } else {
            val binding = ItemChatReceivedBinding.inflate(inflater, parent, false)
            ReceivedViewHolder(binding)
        }
    }

    // Gán dữ liệu tin nhắn vào ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]

        if (holder is SentViewHolder) {
            holder.binding.tvMessage.text = message.message
        } else if (holder is ReceivedViewHolder) {
            holder.binding.tvMessage.text = message.message
        }
    }

    override fun getItemCount() = list.size

    // Hàm cập nhật danh sách tin nhắn mới
    fun update(newList: List<ChatMessage>) {
        this.list = newList
        notifyDataSetChanged()
    }

    // ViewHolder cho tin nhắn gửi đi
    inner class SentViewHolder(val binding: ItemChatSentBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder cho tin nhắn nhận về
    inner class ReceivedViewHolder(val binding: ItemChatReceivedBinding) : RecyclerView.ViewHolder(binding.root)
}