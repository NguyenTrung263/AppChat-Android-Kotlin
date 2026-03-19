package com.example.appchat.view.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.R
import com.example.appchat.databinding.ActivityUserBinding
import com.example.appchat.view.adapter.UsersAdapter
import com.example.appchat.view.auth.LoginActivity
import com.example.appchat.view.chat.ChatActivity
import com.example.appchat.view.chat.MainActivity

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        adapter = UsersAdapter(emptyList()) { user ->

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("receiverId", user.uid)
            intent.putExtra("receiverEmail", user.email)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter

        viewModel.users.observe(this) {
            adapter.update(it)
        }

    }
    // 👇 Inflate Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_users, menu)
        return true
    }

    // 👇 Handle Logout Click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_logout) {

            viewModel.logout()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}