package com.example.yexperi

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yexperi.databinding.ChatListItemBinding
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(var messages : MutableList<UsersDTO>) : RecyclerView.Adapter<ChatAdapter.MessagesViewHolder>() {


    fun updateList(list: MutableList<UsersDTO>){
        messages.clear()
        messages.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return MessagesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val item = messages[position]
        holder.bind(item)

    }
    class MessagesViewHolder private constructor(private val binding: ChatListItemBinding) : RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup) : MessagesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatListItemBinding.inflate(layoutInflater, parent, false)
                return MessagesViewHolder(binding)
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind(
            item: UsersDTO
        ){

            //To differentiate between phone owner and other users
            if(item.userId == FirebaseDatas.userID){
                binding.chatAuthorTextView.text = "Me"
            }
            else binding.chatAuthorTextView.text = item.author

            binding.chatMessageTextView.text = item.message
            binding.chatTimeTextView.text = item.timeStamp!!.convertLongToDateString()
            binding.executePendingBindings()
        }
    }
}