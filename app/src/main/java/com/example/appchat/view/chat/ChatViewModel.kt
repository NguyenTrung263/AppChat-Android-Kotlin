package com.example.appchat.view.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appchat.data.model.ChatMessage
import com.example.appchat.data.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth

class ChatViewModel : ViewModel() {

    private val repo = ChatRepository()

    val messages = MutableLiveData<List<ChatMessage>>()

    fun startChat(receiverId: String) {

        val senderId = FirebaseAuth.getInstance().uid!!

        repo.listenMessage(senderId, receiverId) {
            messages.postValue(it)
        }
    }

    fun send(receiverId: String, text: String) {
        val senderId = FirebaseAuth.getInstance().uid ?: return
        repo.sendMessage(text, senderId, receiverId)
    }

}