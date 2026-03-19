package com.example.appchat.data.repository

import android.util.Log
import com.example.appchat.data.model.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatRepository {
    private val db = FirebaseDatabase.getInstance().getReference("chats")
    fun sendMessage(
        message: String,
        senderId: String,
        receiverId: String
    ){
        val chatId = getChatId(senderId, receiverId)
        val ref = FirebaseDatabase.getInstance()
            .getReference("chats")
            .child(chatId)

        ref.push().setValue(
            ChatMessage(senderId, message)
        )
    }

    fun listenMessage(senderId: String, receiverId: String, onMessageReceived: (List<ChatMessage>) -> Unit) {
        val chatId = getChatId(senderId, receiverId)
        FirebaseDatabase.getInstance()
            .getReference("chats")
            .child(chatId)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<ChatMessage>()
                    snapshot.children.forEach {
                        it.getValue(ChatMessage::class.java)?.let { msg ->
                            list.add(msg)
                        }
                    }
                    Log.d("CHAT_DEBUG", "Số lượng tin nhắn nhận được: ${list.size}")
                    onMessageReceived(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error:", error.message)
                }

            })

    }

    fun getChatId(uid1: String, uid2: String): String {
        return if (uid1 < uid2)
            "${uid1}_${uid2}"
        else
            "${uid2}_${uid1}"
    }

}