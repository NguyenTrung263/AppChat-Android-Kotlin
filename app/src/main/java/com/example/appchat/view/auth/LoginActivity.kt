package com.example.appchat.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appchat.view.chat.MainActivity
import com.example.appchat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.trim().toString(),
                binding.etPassword.text.trim().toString()
            )
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.etEmail.text.trim().toString(),
                binding.etPassword.text.trim().toString()
            )
        }

        viewModel.loginResult.observe(this){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}