package com.umc.addicts.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.umc.addicts.R
import com.umc.addicts.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(){

    private var _binding : FragmentRegisterBinding?= null
    private val binding get() = requireNotNull(_binding) { "binding object is not initialized"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.signUpSignUpBtn.setOnClickListener {
            if(isPasswordEqual()){
                navigateToRegisterInformation()
            }
            else {
                Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener{
            navigateToLogin()
        }
    }

    private fun navigateToRegisterInformation() {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToRegisterInformationFragment(
                id = binding.signUpIdEt.text.toString(),
                password = binding.signUpPasswordEt.text.toString(),
                nickname = binding.etRegisterNickname.text.toString()
            )
        )
    }

    private fun isPasswordEqual(): Boolean {
        return binding.signUpPasswordEt.text.toString() == binding.signUpPasswordCheckEt.text.toString()
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}