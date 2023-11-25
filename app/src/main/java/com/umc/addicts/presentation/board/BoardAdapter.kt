package com.umc.addicts.presentation.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc.addicts.data.model.board.Board
import com.umc.addicts.databinding.ItemBoardBinding
import com.umc.addicts.presentation.utils.toLocalDateTimeString

class BoardAdapter(
    private val onItemClick : (Long) -> Unit
) : ListAdapter<Board, BoardAdapter.BoardViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        return BoardViewHolder(
            ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(currentList[position].copy(
            createdAt = currentList[position].createdAt.toLocalDateTimeString())
        )
    }

    inner class BoardViewHolder(private val binding: ItemBoardBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Board) {
            binding.item = item
            binding.cvBoardItem.setOnClickListener {
                onItemClick(item.boardId)
            }
        }
    }

    companion object {
        val callback = object: DiffUtil.ItemCallback<Board>() {
            override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
                return oldItem.boardId == newItem.boardId
            }
        }
    }
}