package com.example.appchat.view.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appchat.data.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val repo = AuthRepository()

    val loginResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun login(email: String, password: String) {
        repo.login(email, password) { success, error ->
            if (success){
                loginResult.postValue(true)
            }else{
                errorMessage.postValue(error!!)
            }
        }
    }

    fun register(email: String, password: String){
        repo.register(email, password) { success, error ->
            if (success) {
                loginResult.postValue(true)
            } else {
                errorMessage.postValue(error!!)
            }
        }
    }


}