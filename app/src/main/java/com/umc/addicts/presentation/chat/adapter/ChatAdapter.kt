package com.umc.addicts.presentation.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.addicts.data.model.chat.ChatRoomResponse
import com.umc.addicts.databinding.ItemChatRoomBinding

class ChatAdapter(
    val onItemClick: () -> Unit
): ListAdapter<ChatRoomResponse,ChatAdapter.ChatRoomViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        return ChatRoomViewHolder(ItemChatRoomBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ChatRoomViewHolder(private val binding: ItemChatRoomBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ChatRoomResponse) {
                binding.item = item
                binding.root.setOnClickListener{
                    onItemClick()
                }
            }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<ChatRoomResponse>() {
            override fun areItemsTheSame(
                oldItem: ChatRoomResponse,
                newItem: ChatRoomResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ChatRoomResponse,
                newItem: ChatRoomResponse
            ): Boolean {
                return oldItem.targetPeriod == newItem.targetPeriod
            }
        }
    }
}