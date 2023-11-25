package com.umc.addicts.presentation.chat

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentChatBinding
import com.umc.addicts.databinding.FragmentChatRoomBinding
import com.umc.addicts.presentation.chat.adapter.ChatAdapter
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ChatFragment: Fragment() {

    private var _binding : FragmentChatBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initAdapter()
        observeData()
    }

    private fun initViews() {
        binding.btCreationChat.setOnClickListener{
            findNavController().navigate(
                ChatFragmentDirections.actionChatFragmentToChatCreationFragment()
            )
        }
    }

    private fun initAdapter() {
        adapter = ChatAdapter(
            onItemClick = {
                navigateToChatRoom()
            }
        )
        binding.rvChat.adapter = adapter
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewModel.chatRoomList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.chatEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                        Log.e("viewModel", it.error.message.toString())
                    }
                    is UiEvent.Success -> {
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToChatRoom() {
        findNavController().navigate(
            ChatFragmentDirections.actionChatFragmentToChatRoomFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}