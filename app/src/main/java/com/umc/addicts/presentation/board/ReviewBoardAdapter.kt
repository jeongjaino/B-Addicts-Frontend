package com.umc.addicts.presentation.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.databinding.ItemReviewBoardBinding
import com.umc.addicts.presentation.utils.toLocalDateTimeString

class ReviewBoardAdapter(
    private val onItemClick : (Long) -> Unit
): ListAdapter<ReviewBoard, ReviewBoardAdapter.ReviewBoardViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewBoardViewHolder {
        return ReviewBoardViewHolder(ItemReviewBoardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ReviewBoardViewHolder, position: Int) {
        holder.bind(currentList[position].copy(
            createdAt = currentList[position].createdAt.toLocalDateTimeString())
        )
    }

    inner class ReviewBoardViewHolder(val binding: ItemReviewBoardBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ReviewBoard) {
                binding.item = item
                binding.cvReviewBoardItem.setOnClickListener {
                    onItemClick(item.boardId)
                }
            }
        }

    companion object {
        val callback = object: DiffUtil.ItemCallback<ReviewBoard>() {
            override fun areItemsTheSame(oldItem: ReviewBoard, newItem: ReviewBoard): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReviewBoard, newItem: ReviewBoard): Boolean {
                return oldItem.boardId == newItem.boardId
            }
        }
    }
}