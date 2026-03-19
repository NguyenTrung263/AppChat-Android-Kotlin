package com.example.appchat.data.repository

import com.example.appchat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com. google. firebase. database.ValueEventListener
class UserRepository {
    private val ref = FirebaseDatabase.getInstance().getReference("users")

    fun getUsers(callback: (List<User>) -> Unit) {
        var currentUid = FirebaseAuth.getInstance().uid
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<User>()
                snapshot.children.forEach {
                    var user = it.getValue(User::class.java)
                    if (user != null && user.uid != currentUid) {
                        list.add(user)
                    }
                }
                callback(list)
            }
            override fun onCancelled(error: DatabaseError) {}

        })

    }
}