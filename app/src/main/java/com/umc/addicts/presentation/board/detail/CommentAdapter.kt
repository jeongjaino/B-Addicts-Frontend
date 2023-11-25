package com.umc.addicts.presentation.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.addicts.data.model.board.Comment
import com.umc.addicts.databinding.ItemBoardDetailCommentBinding
import com.umc.addicts.presentation.utils.toLocalDateTimeString

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(ItemBoardDetailCommentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position].copy(
            createdAt = currentList[position].createdAt.toLocalDateTimeString())
        )
    }

    inner class CommentViewHolder(val binding: ItemBoardDetailCommentBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Comment) {
                binding.item = item
            }
    }

    companion object {
        val callback = object: DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
        }
    }
}