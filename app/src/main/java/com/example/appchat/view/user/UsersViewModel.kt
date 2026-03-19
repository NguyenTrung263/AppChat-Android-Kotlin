package com.example.appchat.view.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appchat.data.model.User
import com.example.appchat.data.repository.AuthRepository
import com.example.appchat.data.repository.UserRepository

class UsersViewModel : ViewModel() {

    private val repo = UserRepository()
    private val authRepository = AuthRepository()

    val users = MutableLiveData<List<User>>()

    init {
        repo.getUsers {
            users.postValue(it)
        }
    }

    fun logout(){
        authRepository.logout()
    }
}