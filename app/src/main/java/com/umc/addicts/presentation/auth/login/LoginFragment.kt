package com.umc.addicts.presentation.auth.login

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
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentLoginBinding
import com.umc.addicts.presentation.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding : FragmentLoginBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews(){
        binding.goToSignUpButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        binding.signInButton.setOnClickListener {
            viewModel.login()
        }
    }

    private fun observeData() {
        viewModel.loginEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                    }

                    is UiEvent.Success -> {
                        navigateToBoard()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToBoard() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToBoardFragment()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}