package com.example.appchat.data.repository

import com.example.appchat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference


    fun login(email:String, password:String, result:(Boolean,String?) -> Unit) {
         auth.signInWithEmailAndPassword(email,password)
             .addOnCompleteListener {
                 if (it.isSuccessful) {
                     result(true,null)
                 }else{
                     result(false, it.exception?.message)
                 }
             }
    }

    fun register(
        email: String,
        password: String,
        result: (Boolean, String?) -> Unit)
    {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = auth.currentUser!!.uid
                    database.child("users")
                        .child(uid)
                        .setValue(User(uid,email))
                    result(true,null)
                }else{
                    result(false, it.exception?.message)
                }
            }
    }
    fun currentUser() = auth.currentUser
    fun logout() = auth.signOut()
}