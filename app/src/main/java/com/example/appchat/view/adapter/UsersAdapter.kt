package com.example.appchat.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.data.model.User
import com.example.appchat.databinding.ItemUserBinding

class UsersAdapter(private var list: List<User>,
                   private val onClick: (User) -> Unit ) : RecyclerView.Adapter<UsersAdapter.VH>() {
    inner class VH(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return VH(binding)
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val user = list[position]
        holder.binding.tvEmail.text = user.email
        var randChar = user.email.random()
        holder.binding.circleTv.text = randChar.toString()

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount() = list.size

    fun update(newList: List<User>) {
        list = newList
        notifyDataSetChanged()
    }


}