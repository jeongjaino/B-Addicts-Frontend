package com.umc.addicts.presentation.chat

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
import com.umc.addicts.data.model.auth.Toxic
import com.umc.addicts.databinding.FragmentChatCreationBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ChatCreationFragment: Fragment() {

    private var _binding : FragmentChatCreationBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: ChatCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_creation, container, false)
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
        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach{ id ->
                val keyword = (group.findViewById<Chip>(id).text.toString())
                // 관심 위험 심각
                viewModel.setKeyword(keyword)
            }
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.forEach{ id ->
                val degree = (group.findViewById<Chip>(id).text.toString())
                // 관심 위험 심각
                viewModel.setDegree(degree)
            }
        }

        binding.allergyChipGroup.isSingleSelection = true
        binding.chipGroup.isSingleSelection = true

        binding.backButton.setOnClickListener{
            navigateToChat()
        }
    }

    private fun observeData() {
        viewModel.chatEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT)
                    }
                    is UiEvent.Success -> {
                        navigateToChat()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun navigateToChat() {
        findNavController().navigate(
            ChatCreationFragmentDirections.actionChatCreationFragmentToChatFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}