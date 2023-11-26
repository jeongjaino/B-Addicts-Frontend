package com.umc.addicts.presentation.board

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.addicts.R
import com.umc.addicts.data.model.board.Board
import com.umc.addicts.data.model.board.ReviewBoard
import com.umc.addicts.databinding.FragmentBoardBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BoardFragment: Fragment() {

    private var _binding : FragmentBoardBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: BoardViewModel by viewModels()

    private lateinit var boardAdapter: BoardAdapter
    private lateinit var reviewBoardAdapter: ReviewBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        binding.cgBoardType.isSingleSelection = true
        binding.chipBoard.isChecked = true

        binding.cgBoardType.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach{ checkedId ->
                if (checkedId == R.id.chip_review_board) {
                    viewModel.getReviewBoard()
                }
                else {
                    viewModel.getBoard()
                }
            }
        }

        binding.creationBoardButton.setOnClickListener{
            findNavController().navigate(
                BoardFragmentDirections.actionBoardFragmentToBoardCreationFragment()
            )
        }

    }

    private fun observeData() {
        viewModel.boardEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiEvent.Success -> { }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.boardUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is BoardViewModel.BoardUiState.BoardState -> {
                        initBoardAdapter(it.board)
                    }

                    is BoardViewModel.BoardUiState.ReviewBoardState -> {
                        initReviewBoardAdapter(it.reviewBoard)
                    }

                    is BoardViewModel.BoardUiState.Init -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initBoardAdapter(list: List<Board>){
        boardAdapter = BoardAdapter(
            onItemClick = { navigateToDetailBoard(it) }
        )
        binding.rvBoard.adapter = boardAdapter
        binding.rvBoard.layoutManager = LinearLayoutManager(requireContext())
        boardAdapter.submitList(list)
    }

    private fun initReviewBoardAdapter(list: List<ReviewBoard>) {
        reviewBoardAdapter = ReviewBoardAdapter(
            onItemClick = {}
        )
        binding.rvBoard.adapter = reviewBoardAdapter
        binding.rvBoard.layoutManager = LinearLayoutManager(requireContext())
        reviewBoardAdapter.submitList(list)
    }

    private fun navigateToDetailBoard(id: Long) {
        findNavController().navigate(
            BoardFragmentDirections.actionBoardFragmentToBoardDetailFragment(id)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}