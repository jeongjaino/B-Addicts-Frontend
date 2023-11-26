package com.umc.addicts.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentChatRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatRoomFragment: Fragment() {

    private var _binding : FragmentChatRoomBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_room, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.backButton.setOnClickListener{
            findNavController().navigate(
                ChatRoomFragmentDirections.actionChatRoomFragmentToChatFragment()
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}