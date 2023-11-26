package com.umc.addicts.presentation.board.creation

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
import com.google.android.material.chip.Chip
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentBoardCreationBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BoardCreationFragment: Fragment() {

    private var _binding : FragmentBoardCreationBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: BoardCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_creation, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        binding.allergyChipGroup.isSingleSelection = true
        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach{ checkId ->
                val keyword = group.findViewById<Chip>(checkId).text.toString()
                viewModel.setKeyword(keyword)
            }
        }
        binding.backButton.setOnClickListener{
            navigateToBoard()
        }
    }

    private fun observeData() {
        viewModel.boardCreationEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Success -> {
                        navigateToBoard()
                    }

                    is UiEvent.Failure -> {
                       Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToBoard() {
        findNavController().navigate(
            BoardCreationFragmentDirections.actionBoardCreationFragmentToBoardFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}