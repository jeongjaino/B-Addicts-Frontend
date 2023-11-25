package com.umc.addicts.presentation.board.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentBoardDetailBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BoardDetailFragment: Fragment() {

    private var _binding : FragmentBoardDetailBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: BoardDetailViewModel by viewModels()
    private val args: BoardDetailFragmentArgs by navArgs()

    private lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        observeData()
        initAdapter()
    }

    private fun initViews() {
        binding.backButton.setOnClickListener{
            findNavController().navigate(
                BoardDetailFragmentDirections.actionBoardDetailFragmentToBoardFragment()
            )
        }
    }

    private fun initData() {
        viewModel.getBoardById(
            args.boardId
        )
    }

    private fun observeData() {
        viewModel.boardDetailEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                        Log.e("board", it.error.message.toString())
                    }
                    is UiEvent.Success -> {
                        binding.etCommentInput.setText("")
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.commentList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        adapter = CommentAdapter()
        binding.rvBoardDetailComment.adapter = adapter
        binding.rvBoardDetailComment.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}